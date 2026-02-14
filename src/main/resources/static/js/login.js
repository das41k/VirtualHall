function toggleLoginField() {
    const emailField = document.getElementById('emailField');
    const phoneField = document.getElementById('phoneField');
    const usernameInput = document.getElementById('username');
    const phoneInput = document.getElementById('phoneInput');
    const emailSelected = document.querySelector('input[name="loginType"]:checked').value === 'email';

    if (emailSelected) {
        // Режим email
        emailField.style.display = 'block';
        phoneField.style.display = 'none';
        usernameInput.placeholder = 'example@mail.ru';
        usernameInput.value = ''; // Очищаем поле
        usernameInput.focus();
    } else {
        // Режим телефона
        emailField.style.display = 'none';
        phoneField.style.display = 'block';
        usernameInput.placeholder = '+7(123)456-78-90';
        phoneInput.focus();
    }
}

// При отправке формы копируем телефон в поле username
document.querySelector('form').addEventListener('submit', function(e) {
    const loginType = document.querySelector('input[name="loginType"]:checked').value;
    const usernameInput = document.getElementById('username');
    const phoneInput = document.getElementById('phoneInput');

    if (loginType === 'phone' && phoneInput.value) {
        usernameInput.value = phoneInput.value;
    }
});