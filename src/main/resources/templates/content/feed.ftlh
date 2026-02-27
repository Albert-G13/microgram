<#import "../layout.ftlh" as layout>

<@layout.mainLayout title="Лента">
    <style>
        .open-post-btn, .open-post-btn:focus, .open-post-btn:hover {
            text-decoration: none !important;
            box-shadow: none !important;
        }
    </style>

    <div class="row justify-content-center">
        <div class="col-md-6">
            <#list posts as post>
                <div class="card mb-4 shadow-sm">
                    <div class="card-header d-flex align-items-center bg-white border-0 py-3">
                        <img src="/images/${post.authorAvatar!'default-avatar.png'}"
                             class="rounded-circle me-2" width="32" height="32" style="object-fit: cover;">
                        <strong>${post.authorLogin}</strong>
                    </div>

                    <img src="/images/${post.image}" class="card-img p-0" alt="post">

                    <div class="card-body">
                        <div class="mb-2">
                            <button class="btn btn-link p-0 text-dark me-3 like-btn" data-id="${post.id}" style="text-decoration: none;">
                                <span class="heart-icon">❤️</span> <span class="likes-count">${post.likesCount}</span>
                            </button>

                            <button
                                    class="btn btn-link p-0 text-dark open-post-btn"
                                    data-id="${post.id}"
                                    data-image="${post.image}"
                                    data-description="${post.description!""}"
                                    data-author="${post.authorLogin}">
                                💬 <span id="cnt-pub-${post.id}">${post.commentaryCount}</span>
                            </button>
                        </div>

                        <p class="card-text mb-1">
                            <strong>${post.authorLogin}</strong> ${post.description!""}
                        </p>
                        <small class="text-muted d-block mb-3">${post.dateOfPublication}</small>

                        <div class="comments-list border-top pt-2" id="comments-pub-${post.id}">
                            <#if post.comments??>
                                <#list post.comments as comment>
                                    <div class="mb-1 small">
                                        <strong>${comment.authorLogin}:</strong> ${comment.content}
                                    </div>
                                </#list>
                            </#if>
                        </div>
                    </div>

                    <div class="card-footer bg-white border-top-0">
                        <div class="input-group input-group-sm">
                            <input type="text" class="form-control border-0 comment-input"
                                   placeholder="Добавить комментарий..." data-id="${post.id}">
                            <button class="btn btn-link text-primary fw-bold send-comment-btn"
                                    data-id="${post.id}" style="text-decoration: none;">Опубликовать</button>
                        </div>
                    </div>
                </div>
            <#else>
                <div class="text-center mt-5">
                    <h4>Ваша лента пуста. Подпишитесь на кого-нибудь!</h4>
                </div>
            </#list>
        </div>
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
                                <strong id="modalAuthor"></strong>
                                <p id="modalDescription" class="text-muted small mb-0"></p>
                            </div>
                            <div id="commentsContainer" class="p-3 flex-grow-1 overflow-auto" style="background: #fafafa;"></div>
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

    <script>const currentUserLogin = '${user!""}';</script>
    <script src="/js/likes.js"></script>
    <script src="/js/comments.js"></script>
</@layout.mainLayout>