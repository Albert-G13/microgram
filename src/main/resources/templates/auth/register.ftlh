<#import "../layout.ftlh" as layout>

<@layout.mainLayout title="Регистрация">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card p-4 shadow-sm">
                <h3 class="text-center mb-4">Регистрация</h3>
                <form action="/register" method="post">
                    <div class="mb-3">
                        <label class="form-label">Логин</label>
                        <input type="text" name="login" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Имя</label>
                        <input type="text" name="name" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Пароль</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Создать аккаунт</button>
                </form>
                <div class="mt-3 text-center">
                    <span>Есть аккаунт? <a href="/login">Войти</a></span>
                </div>
            </div>
        </div>
    </div>
</@layout.mainLayout>