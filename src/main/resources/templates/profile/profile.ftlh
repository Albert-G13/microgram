<#import "../layout.ftlh" as layout>

<@layout.mainLayout title="Профиль ${profile.login}">
    <div class="row mb-5">
        <div class="col-md-4 text-center">
            <div class="position-relative d-inline-block">
                <img src="/avatars/${profile.avatar!'default-avatar.png'}"
                     id="avatar-image"
                     class="rounded-circle img-thumbnail"
                     style="width: 150px; height: 150px; object-fit: cover; <#if isMyProfile>cursor: pointer;</#if>"
                     <#if isMyProfile>onclick="document.getElementById('avatar-input').click();"</#if>>

                <#if isMyProfile>
                    <form id="avatar-form" action="/profile/update-avatar" method="post" enctype="multipart/form-data" class="d-none">
                        <input type="file" name="file" id="avatar-input" accept="image/*" onchange="submitAvatar()">
                    </form>
                    <div class="small text-muted mt-2">Нажмите на фото, чтобы изменить</div>
                </#if>
            </div>
        </div>
        <div class="col-md-8">
            <div class="d-flex align-items-center mb-3">
                <h2 class="me-3">${profile.login}</h2>
                <#if isMyProfile>
                    <a href="/profile/edit" class="btn btn-outline-secondary btn-sm">Редактировать профиль</a>
                <#else>
                    <form action="/follow/${profile.login}" method="post">
                        <button type="submit" class="btn btn-primary btn-sm">Подписаться</button>
                    </form>
                </#if>
            </div>
            <div class="d-flex mb-3">
                <div class="me-4"><strong>${posts?size}</strong> публикаций</div>
                <div class="me-4"><strong>${profile.followerCount!0}</strong> подписчиков</div>
                <div class="me-4"><strong>${profile.followingCount!0}</strong> подписок</div>
            </div>
            <div>
                <strong>${profile.name!""}</strong>
                <p>${profile.info!""}</p>
            </div>
        </div>
    </div>

    <hr>

    <div class="row">
        <#list posts as post>
            <div class="col-4 mb-4">
                <div class="ratio ratio-1x1" style="cursor: pointer;"
                     onclick="openPostModal('${post.id}', '${post.image}', '${post.description!""}')">
                    <img src="/images/${post.image}" class="img-fluid object-fit-cover rounded" alt="post">
                </div>
            </div>
        <#else>
            <div class="text-center w-100">
                <p class="text-muted">Публикаций пока нет</p>
            </div>
        </#list>
    </div>

    <div class="modal fade" id="postModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-xl modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-body p-0">
                    <div class="row g-0">
                        <div class="col-md-8 bg-black d-flex align-items-center justify-content-center">
                            <img src="" id="modalImage" class="img-fluid" style="max-height: 80vh;">
                        </div>
                        <div class="col-md-4 d-flex flex-column" style="height: 80vh;">
                            <div class="p-3 border-bottom">
                                <strong id="modalAuthor">${profile.login}</strong>
                                <p id="modalDescription" class="text-muted small mb-0"></p>
                            </div>
                            <div id="commentsContainer" class="p-3 flex-grow-1 overflow-auto" style="background: #fafafa;">
                            </div>
                            <div class="p-3 border-top">
                                <div class="input-group">
                                    <input type="text" id="commentInput" class="form-control" placeholder="Добавить комментарий...">
                                    <button class="btn btn-outline-primary" type="button" onclick="sendComment()">Опубликовать</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#if isMyProfile>
        <script>
            function submitAvatar() {
                console.log("Файл выбран, отправляю форму...");
                const form = document.getElementById('avatar-form');
                if (form) {
                    form.submit();
                }
            }
        </script>
    </#if>
    <script>
        let currentPostId = null;

        const currentUserLogin = '${profile.login}';

        function openPostModal(postId, image, description) {
            currentPostId = postId;
            document.getElementById('modalImage').src = '/images/' + image;
            document.getElementById('modalDescription').innerText = description;

            const container = document.getElementById('commentsContainer');
            container.innerHTML = '<div class="text-center"><div class="spinner-border spinner-border-sm"></div></div>';

            new bootstrap.Modal(document.getElementById('postModal')).show();

            fetch('/api/comments?publicationId=' + postId)
                .then(response => response.json())
                .then(comments => {
                    container.innerHTML = '';
                    <#noparse>
                    comments.forEach(c => {
                        const author = c.authorLogin || 'Аноним';
                        const text = c.content || '';
                        const deleteBtn = (author === currentUserLogin)
                            ? `<span class="text-danger ms-2" style="cursor:pointer" onclick="deleteComment(${c.id})">&times;</span>`
                            : '';

                        container.innerHTML += `
        <div class="mb-2 border-bottom pb-1 d-flex justify-content-between align-items-start">
            <div>
                <strong class="text-primary">${author}:</strong>
                <span>${text}</span>
            </div>
            ${deleteBtn}
        </div>
    `;
                    });
                    </#noparse>
                });
        }

        function sendComment() {
            const text = document.getElementById('commentInput').value;
            if (!text) return;

            fetch('/api/comments', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ publicationId: currentPostId, content: text })
            }).then(response => {
                if (response.ok) {
                    document.getElementById('commentInput').value = '';
                    openPostModal(currentPostId, document.getElementById('modalImage').src.split('/').pop(), document.getElementById('modalDescription').innerText);
                }
            });
        }

        function deleteComment(commentId) {
            if (!confirm("Удалить этот комментарий?")) return;

            fetch('/api/comments/' + commentId, {
                method: 'DELETE'
            }).then(response => {
                if (response.ok) {
                    const currentImgName = document.getElementById('modalImage').src.split('/').pop();
                    const currentDesc = document.getElementById('modalDescription').innerText;
                    openPostModal(currentPostId, currentImgName, currentDesc);
                } else {
                    alert("Не удалось удалить комментарий.");
                }
            });
        }
    </script>
</@layout.mainLayout>