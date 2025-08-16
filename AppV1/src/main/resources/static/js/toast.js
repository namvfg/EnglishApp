/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', () => {
  const toastEl = document.getElementById('liveToast');
  if (toastEl && window.bootstrap?.Toast) {
    const toast = new bootstrap.Toast(toastEl, { delay: 3000, autohide: true });
    toast.show();
  }
});