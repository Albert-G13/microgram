<#import "../layout.ftlh" as layout>

<@layout.mainLayout title="Редактировать профиль">
    <div class="row justify-content-center mt-5">
        <div class="col-md-5 card shadow-sm p-4">
            <h4 class="text-center mb-4">Настройки профиля</h4>

            <form action="/profile/edit" method="post" enctype="multipart/form-data">
                <#if _csrf??><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></#if>

                <div class="text-center mb-3">
                    <img src="/avatars/${user.avatar!'default-avatar.png'}"
                         class="rounded-circle border" width="100" height="100" style="object-fit: cover;">
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Новое фото</label>
                    <input type="file" name="avatar" class="form-control form-control-sm" accept="image/*">
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">Имя</label>
                    <input type="text" name="name" class="form-control" value="${user.name!''}">
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold">О себе</label>
                    <textarea name="info" class="form-control" rows="3">${user.info!''}</textarea>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                    <a href="/profile/${user.login}" class="btn btn-light border">Назад</a>
                </div>
            </form>
        </div>
    </div>
</@layout.mainLayout>