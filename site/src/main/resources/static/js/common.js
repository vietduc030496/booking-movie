function changeLanguage(lang) {
    const url = new URL(window.location);
    url.searchParams.set('lang', lang);
    window.location.href = url.toString();
}

// Play movie's trailer
document.querySelectorAll('.play-button').forEach(el => {
    el.addEventListener('click', function() {
        const title = this.getAttribute('data-title');
        const trailerUrl = this.getAttribute('data-trailer');
        viewTrailer(title, trailerUrl);
    });
});

function viewTrailer(movieTitle, videoUrl) {
    if (!videoUrl || videoUrl.trim() === "") {
        let msgError = document.getElementById('trailer-notfound').innerText;
        alert(msgError);
        return;
    }

    document.getElementById('trailerModalLabel').textContent ='TRAILER - ' + movieTitle;
    const iframe = document.getElementById('trailerIframe');
    iframe.src = videoUrl + "?autoplay=1";
    const modal = new bootstrap.Modal(document.getElementById('trailerModal'));
    modal.show();
}

// Pause video trailer before close modal
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('trailerModal');
    const iframe = document.getElementById('trailerIframe');

    modal.addEventListener('hidden.bs.modal', function () {
        iframe.src = '';
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const triggerTabList = document.querySelectorAll('[data-bs-toggle="tab"]');

    triggerTabList.forEach(function (triggerEl) {
        triggerEl.addEventListener('shown.bs.tab', function (event) {
            // Loại bỏ tab-active khỏi tất cả <li>
            document.querySelectorAll('.tab-films li').forEach(function (li) {
                li.classList.remove('tab-active');
            });

            // Thêm tab-active cho <li> hiện tại
            event.target.closest('li').classList.add('tab-active');
        });
    });
});

// Hiện submenu khi rê chuột vào
document.querySelectorAll('.dropdown-submenu').forEach(function (element) {
    element.addEventListener('mouseenter', function () {
        const submenu = element.querySelector('.dropdown-menu');
        if (submenu) submenu.style.display = 'block';
    });
    element.addEventListener('mouseleave', function () {
        const submenu = element.querySelector('.dropdown-menu');
        if (submenu) submenu.style.display = 'none';
    });
});

// Toggle dropdown cấp 1 khi click vào .dropdown-toggle
document.querySelectorAll('.dropdown-toggle').forEach(function (toggle) {
    toggle.addEventListener('click', function (e) {
        e.preventDefault(); // ngăn điều hướng
        const parent = toggle.parentElement;
        const menu = parent.querySelector('.dropdown-menu');

        // Đóng các menu khác nếu đang mở
        document.querySelectorAll('.dropdown-menu').forEach(function (m) {
            if (m !== menu) m.style.display = 'none';
        });

        // Toggle menu hiện tại
        if (menu) {
            menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
        }
    });
});

// Tắt dropdown khi click ra ngoài
document.addEventListener('click', function (e) {
    const isClickInside = e.target.closest('.dropdown');
    if (!isClickInside) {
        document.querySelectorAll('.dropdown-menu').forEach(function (menu) {
            menu.style.display = 'none';
        });
    }
});

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

document.addEventListener('DOMContentLoaded', function () {
    const selectedTheaterId = getCookie('selectedTheaterId');
    if (!selectedTheaterId) {
        // Nếu chưa chọn, show modal
        const modal = new bootstrap.Modal(document.getElementById('selectTheaterModal'));
        modal.show();

        document.getElementById('confirmTheaterBtn').addEventListener('click', function () {
            const selectedId = document.getElementById('theaterSelect').value;
            if (selectedId) {
                document.cookie = "selectedTheaterId=" + selectedId + "; path=/";
                document.cookie = "selectedTheaterName=" + selectedId + "; path=/";
                modal.hide();
                location.reload();
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function () {
    if (typeof provinces === 'undefined') {
        console.error('Chưa có dữ liệu provinces!');
        return;
    }

    const provinceSelect = document.getElementById('provinceSelect');
    const theaterSelect = document.getElementById('theaterSelect');
    const confirmBtn = document.getElementById('confirmTheaterBtn');

    const provinceMap = new Map();
    provinces.forEach(p => provinceMap.set(p.provinceCode, p.theaters));

    provinceSelect.addEventListener('change', function () {
        const selectedProvinceId = this.value;

        // Delete old options
        theaterSelect.innerHTML = '<option value="" disabled selected>-- Chọn rạp --</option>';

        if (selectedProvinceId && provinceMap.has(selectedProvinceId)) {
            const theaters = provinceMap.get(selectedProvinceId);

            theaters.forEach(t => {
                const option = document.createElement('option');
                option.value = t.theaterId;
                option.textContent = t.theaterName;
                theaterSelect.appendChild(option);
            });

            theaterSelect.disabled = false;
            confirmBtn.disabled = true; // chờ chọn rạp
        } else {
            theaterSelect.disabled = true;
            confirmBtn.disabled = true;
        }
    });

    theaterSelect.addEventListener('change', function () {
        confirmBtn.disabled = !this.value;
    });
});

function viewsShowtimes(movieId, movieName) {
    let theaterId = getCookie('selectedTheaterId');
    fetch('/views/showtimes/movies/' + movieId + '/theaters/' + theaterId)
        .then(response => response.text())
        .then(html => {
            document.getElementById('showtimeModalContainer').innerHTML = html;
            document.getElementById('showtimeModalLabel').innerText = movieName;
            document.getElementById('theaterNameH1').textContent = getCookie('selectedTheaterName');
            const modalEl = document.getElementById('showtimeModal');
            const modal = new bootstrap.Modal(modalEl);
            modal.show();
    });

}