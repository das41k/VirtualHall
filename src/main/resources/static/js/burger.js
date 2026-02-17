document.addEventListener('DOMContentLoaded', function() {
    const burgerBtn = document.getElementById('burgerBtn');
    const headerNav = document.getElementById('headerNav');
    const menuOverlay = document.getElementById('menuOverlay');

    // Проверка наличия элементов (чтобы не было ошибок на страницах без меню)
    if (!burgerBtn || !headerNav) return;

    function toggleMenu() {
        burgerBtn.classList.toggle('active');
        headerNav.classList.toggle('active');

        if (menuOverlay) {
            menuOverlay.classList.toggle('active');
        }

        // Блокируем скролл страницы при открытом меню
        document.body.style.overflow = headerNav.classList.contains('active') ? 'hidden' : '';
    }

    // Открытие/закрытие по клику на бургер
    burgerBtn.addEventListener('click', toggleMenu);

    // Закрытие при клике на затемнение
    if (menuOverlay) {
        menuOverlay.addEventListener('click', toggleMenu);
    }

    // Закрытие при клике на ссылку меню
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', toggleMenu);
    });
});