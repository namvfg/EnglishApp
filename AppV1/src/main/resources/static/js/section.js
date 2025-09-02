/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const contextPath = document.querySelector('meta[name="context-path"]')?.content || '';
let editorInstance;
const contentEl = document.querySelector('#content');
if (contentEl) {
    ClassicEditor.create(contentEl, {
        toolbar: [
            'heading', '|', 'bold', 'italic', 'underline', 'link', 'bulletedList', 'numberedList',
            '|', 'blockQuote', 'insertTable', 'undo', 'redo', '|', 'imageUpload'
        ],
        ckfinder: {
            uploadUrl: `${contextPath}/api/ckeditor/upload-image`
        }
    })
            .then(ed => editorInstance = ed)
            .catch(console.error);
}