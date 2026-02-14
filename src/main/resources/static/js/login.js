// Функция для переключения полей ввода при выборе радио-кнопки
function toggleLoginField() {
    console.log("toggleLoginField вызван"); // для отладки

    // Получаем все радио-кнопки с именем loginType
    var radioButtons = document.getElementsByName('loginType');
    var emailField = document.getElementById('emailField');
    var phoneField = document.getElementById('phoneField');
    var emailInput = document.getElementById('email');
    var phoneInput = document.getElementById('phone');

    // Проверяем, существуют ли элементы
    if (!emailField || !phoneField || !emailInput || !phoneInput) {
        console.log("Элементы не найдены на странице");
        return;
    }

    // Находим выбранную радио-кнопку
    var selectedValue = 'email'; // значение по умолчанию
    for (var i = 0; i < radioButtons.length; i++) {
        if (radioButtons[i].checked) {
            selectedValue = radioButtons[i].value;
            break;
        }
    }

    console.log("Выбрано: " + selectedValue);

    if (selectedValue === 'email') {
        // Показываем поле для email, скрываем для телефона
        emailField.style.display = 'block';
        phoneField.style.display = 'none';

        // Устанавливаем required атрибуты
        emailInput.required = true;
        phoneInput.required = false;

        // Очищаем поле телефона
        phoneInput.value = '';
    } else {
        // Показываем поле для телефона, скрываем для email
        emailField.style.display = 'none';
        phoneField.style.display = 'block';

        // Устанавливаем required атрибуты
        emailInput.required = false;
        phoneInput.required = true;

        // Очищаем поле email
        emailInput.value = '';
    }
}

// Вызываем функцию после полной загрузки DOM
document.addEventListener('DOMContentLoaded', function() {
    console.log("DOM загружен");

    // Проверяем, есть ли на странице радио-кнопки
    var radioButtons = document.getElementsByName('loginType');

    if (radioButtons.length > 0) {
        // Устанавливаем начальное состояние
        toggleLoginField();
        console.log("Начальное состояние установлено");
    } else {
        console.log("Радио-кнопки не найдены");
    }
});

// Для отладки - добавим обработчик на все радио-кнопки
document.addEventListener('DOMContentLoaded', function() {
    var radioButtons = document.getElementsByName('loginType');
    for (var i = 0; i < radioButtons.length; i++) {
        radioButtons[i].addEventListener('click', function() {
            console.log("Радио-кнопка нажата");
        });
    }
});