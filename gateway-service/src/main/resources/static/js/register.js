 let currentStep = 1;
        const totalSteps = 3;

        function nextStep() {
            if (currentStep < totalSteps) {
                currentStep++;
                updateUI();
            } else {
                alert('Регистрация прошла успешно');
            }
        }

        function previousStep() {
            if (currentStep > 1) {
                currentStep--;
                updateUI();
            }
        }

        function updateUI() {
            // Update progress bar
            const progressFill = document.querySelector('.progress-fill');
            progressFill.style.width = (currentStep / totalSteps * 100) + '%';

            // Update step indicators
            const indicators = document.querySelectorAll('.step-indicator');
            indicators.forEach((indicator, index) => {
                const stepNum = index + 1;
                if (stepNum < currentStep) {
                    indicator.className = 'step-indicator step-completed';
                    indicator.innerHTML = '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20,6 9,17 4,12"/></svg>';
                } else if (stepNum === currentStep) {
                    indicator.className = 'step-indicator step-active';
                    indicator.innerHTML = '<span>' + stepNum + '</span>';
                } else {
                    indicator.className = 'step-indicator step-inactive';
                    indicator.innerHTML = '<span>' + stepNum + '</span>';
                }
            });

            // Update connectors
            const connectors = document.querySelectorAll('.step-connector');
            connectors.forEach((connector, index) => {
                if (index + 1 < currentStep) {
                    connector.classList.add('active');
                } else {
                    connector.classList.remove('active');
                }
            });

            // Update step text
            const stepTexts = ['Создайте аккаунт', 'Расскажите нам о себе', 'Подтвердите почту'];
            document.querySelector('.muted-foreground').textContent = `Шаг ${currentStep} из ${totalSteps}: ${stepTexts[currentStep - 1]}`;

            // Update form content
            updateFormContent();

            // Update navigation buttons
            updateNavigationButtons();
        }

        function updateFormContent() {
            const formContainer = document.querySelector('.space-y-6 .space-y-4');
            
            if (currentStep === 1) {
                formContainer.innerHTML = `
                    <div class="space-y-2">
                        <label for="Почта">Почта</label>
                        <input id="Почта" type="Почта" placeholder="Введите почту" class="input-field" />
                    </div>
                    <div class="space-y-2">
                        <label for="Пароль">Пароль</label>
                        <input id="Пароль" type="Пароль" placeholder="Введите пароль" class="input-field" />
                    </div>
                    <div class="space-y-2">
                        <label for="Подтвердите пароль">Подтвердите пароль</label>
                        <input id="Подтвердите пароль" type="Пароль" placeholder="Подтвердите пароль" class="input-field" />
                    </div>
                `;
            } else if (currentStep === 2) {
                formContainer.innerHTML = `
                    <div class="grid grid-cols-2 gap-4">
                        <div class="space-y-2">
                            <label for="Имя">Имя</label>
                            <input id="Имя" placeholder="Сашка" class="input-field" />
                        </div>
                        <div class="space-y-2">
                            <label for="Фамилия">Фамилия</label>
                            <input id="Фамилия" placeholder="Какашка" class="input-field" />
                        </div>
                    </div>
                    <div class="space-y-2">
                        <label for="Номер телефона">Номер телефона</label>
                        <input id="Номер телефона" placeholder="Введите номер телефона" class="input-field" />
                    </div>
                    <div class="space-y-2">
                        <label for="Дата рождения">Дата рождения</label>
                        <input id="Дата рождения" placeholder="Введите дату рождения" class="input-field" />
                    </div>
                `;
            } else if (currentStep === 3) {
                formContainer.innerHTML = `
                    <div class="text-center space-y-4">
                        <div class="w-16 h-16 rounded-full flex items-center justify-center mx-auto" style="background-color: rgba(132, 255, 254, 0.1);">
                            <svg class="w-8 h-8" style="color: var(--primary);" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                            </svg>
                        </div>
                        <div>
                            <h3>Проверьте свою электронную почту</h3>
                            <p class="muted-foreground mt-2">
                               Мы отправили ссылку для подтверждения<br />
                                <strong>по адресу your@email.com</strong>
                            </p>
                            
                        </div>
                        <button class="btn-primary w-full" style="background-color: transparent; border: 1px solid var(--border); color: var(--foreground);">
                            Resend verification email
                        </button>
                    </div>
                `;
            }
        }

        function updateNavigationButtons() {
            const prevButton = document.querySelector('.btn-ghost');
            const nextButton = document.querySelector('.btn-primary:last-child');

            // Update previous button
            if (currentStep === 1) {
                prevButton.disabled = true;
                prevButton.style.opacity = '0.5';
            } else {
                prevButton.disabled = false;
                prevButton.style.opacity = '1';
                prevButton.onclick = previousStep;
            }

            // Update next button
            if (currentStep < totalSteps) {
                nextButton.innerHTML = `
                    Далее
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M5 12h14"/>
                        <path d="m12 5 7 7-7 7"/>
                    </svg>
                `;
                nextButton.onclick = nextStep;
            } else {
                nextButton.innerHTML = `
                     Отправить повторно
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <polyline points="20,6 9,17 4,12"/>
                    </svg>
                `;
                nextButton.onclick = () => alert('Войти');
            }
        }

        // Add some basic styling for grid layout
        const style = document.createElement('style');
        style.textContent = `
            .grid { display: grid; }
            .grid-cols-2 { grid-template-columns: repeat(2, minmax(0, 1fr)); }
            .gap-4 { gap: 1rem; }
            .w-full { width: 100%; }
            .w-16 { width: 4rem; }
            .h-16 { height: 4rem; }
            .space-y-2 > * + * { margin-top: 0.5rem; }
            .space-y-4 > * + * { margin-top: 1rem; }
            .space-y-6 > * + * { margin-top: 1.5rem; }
            .space-y-8 > * + * { margin-top: 2rem; }
            .text-center { text-align: center; }
            .mt-2 { margin-top: 0.5rem; }
            .mx-auto { margin-left: auto; margin-right: auto; }
            .text-sm { font-size: 0.875rem; }
            .rounded-full { border-radius: 9999px; }
            .flex { display: flex; }
            .items-center { align-items: center; }
            .justify-center { justify-content: center; }
            .justify-between { justify-content: space-between; }
            .min-h-screen { min-height: 100vh; }
            .max-w-md { max-width: 28rem; }
            .p-8 { padding: 2rem; }
        `;
        document.head.appendChild(style);
    