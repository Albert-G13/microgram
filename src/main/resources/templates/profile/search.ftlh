<#import "../layout.ftlh" as layout>

<@layout.mainLayout title="Поиск пользователей">
    <div class="container mt-4">
        <h2 class="mb-4">Поиск в Microgram</h2>

        <form action="/search" method="get" class="mb-5">
            <div class="input-group">
                <input type="text" name="query" class="form-control"
                       placeholder="Введите логин, имя или email..."
                       value="${query!''}" autofocus>
                <button class="btn btn-primary" type="submit">Найти</button>
            </div>
        </form>

        <div class="list-group">
            <#if users?? && users?size gt 0>
                <#list users as user>
                    <a href="/profile/${user.login}" class="fw-bold text-decoration-none text-dark">
                        <div class="list-group-item d-flex justify-content-between align-items-center shadow-sm mb-2 rounded">
                            <div class="d-flex align-items-center">
                                <img src="/avatars/${user.avatar!'default-avatar.png'}"
                                     class="rounded-circle me-3"
                                     style="width: 50px; height: 50px; object-fit: cover;">
                                <div>
                                    <a href="/profile/${user.login}" class="fw-bold text-decoration-none text-dark">
                                        @${user.login}
                                    </a>
                                    <div class="text-muted small">${user.name!""}</div>
                                </div>
                            </div>

                            <form action="/follow/${user.login}" method="post">
                                <button type="submit" class="btn btn-primary btn-sm">Подписаться</button>
                            </form>
                        </div>
                    </a>
                </#list>
            <#elseif query??>
                <div class="alert alert-info">По вашему запросу "${query}" ничего не найдено.</div>
            </#if>
        </div>
    </div>
</@layout.mainLayout>