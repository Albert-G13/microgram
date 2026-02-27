<#import "../layout.ftlh" as layout>

<@layout.mainLayout title="Вход">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card p-4 shadow-sm">
                <h3 class="text-center mb-4">Microgram</h3>

                <#if error??>
                    <div class="alert alert-danger">${error}</div>
                </#if>

                <form action="/login" method="post">
                    <div class="mb-3">
                        <label class="form-label">Email</label>
                        <input type="text" name="username" class="form-control" placeholder="example@mail.com" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Пароль</label>
                        <input type="password" name="password" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Войти</button>
                </form>
                <div class="mt-3 text-center">
                    <span>Нет аккаунта? <a href="/register">Зарегистрироваться</a></span>
                </div>
            </div>
        </div>
    </div>
</@layout.mainLayout>