// Pause video trailer before close modal
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('trailerModal');
    const iframe = document.getElementById('trailerIframe');

    modal.addEventListener('hidden.bs.modal', function () {
        iframe.src = '';
    });
});