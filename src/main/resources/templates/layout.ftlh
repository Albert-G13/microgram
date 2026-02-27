<#macro mainLayout title="Microgram">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
        <style>
            body { background-color: #fafafa; padding-top: 70px; }
            .navbar { background-color: #fff; border-bottom: 1px solid #dbdbdb; height: 60px; }
            .nav-link i { font-size: 1.5rem; color: #262626; }
            .navbar-brand { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; font-weight: bold; font-size: 1.5rem; }
        </style>
    </head>
    <body>

    <nav class="navbar navbar-expand navbar-light fixed-top">
        <div class="container d-flex justify-content-between">
            <a class="navbar-brand" href="/feed">Microgram</a>

            <div class="navbar-nav align-items-center">
                <a class="nav-link px-2" href="/feed" title="Лента">
                    <i class="bi bi-house-door"></i>
                </a>

                <a class="nav-link px-2" href="/search" title="Поиск">
                    <i class="bi bi-search"></i>
                </a>

                <a class="nav-link px-2" href="/publications/add" title="Добавить фото">
                    <i class="bi bi-plus-square"></i>
                </a>

                <a class="nav-link px-2" href="/profile" title="Мой профиль">
                    <i class="bi bi-person-circle"></i>
                </a>

                <form action="/logout" method="post" class="ms-2">
                    <button type="submit" class="btn btn-link nav-link p-0">
                        <i class="bi bi-box-arrow-right"></i>
                    </button>
                </form>
            </div>
        </div>
    </nav>

    <div class="container">
        <#nested>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/js/comments.js"></script>
    </body>
    </html>
</#macro>