/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

const contextPath = document.querySelector('meta[name="context-path"]')?.content || '';

//load default lessonType
window.addEventListener('load', function () {
    const skill = document.getElementById('skill').value;
    if (skill) {
        const event = new Event('change');
        document.getElementById('skill').dispatchEvent(event);
    }
});


//load lessonType by skill
let editorInstance = null;

const skillEl = document.getElementById('skill');
const audioDiv = document.getElementById('audio-upload');
const textDiv = document.getElementById('text-upload');
const contentEl = document.getElementById('content');
const formEl = document.querySelector('form'); // form:form

// Đồng bộ CKEditor -> textarea trước khi submit
formEl.addEventListener('submit', (e) => {
    const skill = (skillEl.value || '').toLowerCase();
    if (skill !== 'listening') {
        // đang ở chế độ text => cần set lại value
        if (editorInstance) {
            contentEl.value = editorInstance.getData(); // <--- quan trọng
        }
        // đảm bảo textarea có thể submit
        contentEl.disabled = false;
        contentEl.setAttribute('name', 'content');
    } else {
        // chế độ listening => không submit textarea
        contentEl.disabled = true;
        contentEl.removeAttribute('name');
    }
});

// Toggle theo skill
skillEl.addEventListener('change', function () {
    const skill = (this.value || '').toLowerCase();
    const fileInput = audioDiv?.querySelector("input[type='file']");

    if (skill === 'listening') {
        if (editorInstance) {
            editorInstance.destroy().then(() => {
                editorInstance = null;
            }).catch(console.error);
        }
        audioDiv.style.display = 'block';
        textDiv.style.display = 'none';
        contentEl.disabled = true;
        contentEl.removeAttribute('name');
        contentEl.value = '';
        if (fileInput)
            fileInput.required = true;

    } else {
        audioDiv.style.display = 'none';
        textDiv.style.display = 'block';
        contentEl.disabled = false;
        contentEl.setAttribute('name', 'content');
        if (fileInput)
            fileInput.required = false;

        if (!editorInstance && contentEl) {
            ClassicEditor.create(contentEl, {
                toolbar: [
                    'heading', '|', 'bold', 'italic', 'underline', 'link', 'bulletedList', 'numberedList',
                    '|', 'blockQuote', 'insertTable', 'undo', 'redo'
                ]
            }).then(ed => editorInstance = ed)
                    .catch(console.error);
        }
    }

    // load lesson types 
    fetch(`${contextPath}/api/lesson-types?skill=${encodeURIComponent(skill)}`)
            .then(r => r.json())
            .then(data => {
                const select = document.getElementById('lessonType');
                if (!select)
                    return;
                select.innerHTML = '';
                data.forEach(type => {
                    const opt = document.createElement('option');
                    opt.value = type.id;
                    opt.text = type.name;
                    select.appendChild(opt);
                });
            })
            .catch(err => console.error('Load lesson types failed:', err));
});


const modalEl = document.getElementById('sectionModal');
const titleEl = document.getElementById('sectionModalTitle');
const secIdEl = document.getElementById('secId');
const secContentEl = document.getElementById('secContent');    // <textarea>
const sectionTypeEl = document.getElementById('sectionTypeId');   // <select>
const secformEl = document.getElementById('sectionForm');

let secEditorInstance = null;
let pendingContent = ''; // lưu tạm nội dung cần set vào editor

// Tạo editor (nếu chưa có) khi modal hiển thị xong
modalEl.addEventListener('shown.bs.modal', async () => {
    if (!secEditorInstance) {
        secEditorInstance = await ClassicEditor.create(secContentEl, {
            toolbar: [
                'heading', '|', 'bold', 'italic', 'underline', 'link', 'bulletedList', 'numberedList',
                '|', 'blockQuote', 'insertTable', 'undo', 'redo'
            ]
        });
    }
    // Đồng bộ nội dung vào editor sau khi chắc chắn đã có instance
    secEditorInstance.setData(pendingContent || secContentEl.value || '');
    // Focus editor thay vì focus textarea
    secEditorInstance.editing.view.focus();
});

// Chuẩn bị dữ liệu trước khi mở modal
modalEl.addEventListener('show.bs.modal', (event) => {
    const trigger = event.relatedTarget || null;
    const mode = trigger?.dataset.mode || 'create';

    if (mode === 'create') {
        titleEl.textContent = 'Thêm section';
        secIdEl.value = '';
        secContentEl.value = '';
        pendingContent = '';
        if (sectionTypeEl)
            sectionTypeEl.value = '';
    } else {
        titleEl.textContent = 'Sửa section';
        secIdEl.value = trigger?.dataset.id || '';
        const content = trigger?.dataset.content || '';
        secContentEl.value = content;
        pendingContent = content;

        const sectionTypeId = trigger?.dataset.sectionTypeId || '';
        if (sectionTypeEl) {
            sectionTypeEl.value = sectionTypeId;
            if (sectionTypeEl.value !== sectionTypeId)
                sectionTypeEl.value = '';
        }
    }

    if (secEditorInstance)
        secEditorInstance.setData(pendingContent);
});

modalEl.addEventListener('shown.bs.modal', function () {
    secContentEl?.focus();
});

// Blur trước khi modal đóng để tránh cảnh báo aria-hidden
modalEl.addEventListener('hide.bs.modal', function () {
    if (document.activeElement && modalEl.contains(document.activeElement)) {
        document.activeElement.blur();
    }
});

document.addEventListener('hidden.bs.modal', function (event) {
    // Remove the focus from the active element
    if (document.activeElement) {
        document.activeElement.blur();
    }
});

secformEl.addEventListener('submit', async (e) => {
    e.preventDefault();

    if (window.secEditorInstance) {
        secContentEl.value = secEditorInstance.getData();
    }
    if (!sectionTypeEl.value || !secContentEl.value.trim()) {
        alert('Chọn Section type và nhập nội dung.');
        return;
    }

    const id = (secIdEl.value || '').trim();
    const lessonId = document.querySelector('#lessonId').value; // HIDDEN trong form

    const payload = {
        lessonId: Number(lessonId), // <-- THÊM
        sectionTypeId: Number(sectionTypeEl.value),
        content: secContentEl.value
    };
    
    const base = `${contextPath}/api/sections`;
    const url = id ? `${base}/${id}` : base;
    const method = id ? 'PUT' : 'POST';

    const headers = {'Content-Type': 'application/json;charset=utf-8'};
    const csrf = document.querySelector('meta[name="_csrf"]')?.content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
    if (csrf && csrfHeader)
        headers[csrfHeader] = csrf;

    console.log('[SUBMIT]', {url, method, payload});

    const res = await fetch(url, {method, headers, body: JSON.stringify(payload), credentials: 'same-origin'});
    console.log('HTTP', res.status, res.statusText);

    if (!res.ok) {
        const text = await res.text();
        console.error('Error body:', text);
        alert(`Lưu thất bại (${res.status})`);
        return;
    }

    bootstrap.Modal.getInstance(document.getElementById('sectionModal'))?.hide();
    location.reload();
});

