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
                    '|', 'blockQuote', 'insertTable', 'undo', 'redo', '|', 'imageUpload'
                ],
                ckfinder: {
                    uploadUrl: `${contextPath}/api/ckeditor/upload-image`
                }
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


