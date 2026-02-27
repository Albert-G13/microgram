window.currentPostId = null;

window.openPostModal = function(postId, image, description, author) {
    window.currentPostId = postId;

    document.getElementById('modalImage').src = '/images/' + image;
    document.getElementById('modalDescription').innerText = description || '';
    document.getElementById('modalAuthor').innerText = author || '';

    const container = document.getElementById('commentsContainer');
    container.innerHTML = `<div class="text-center p-3"><div class="spinner-border spinner-border-sm"></div></div>`;

    bootstrap.Modal.getOrCreateInstance(document.getElementById('postModal')).show();

    fetch('/api/comments?publicationId=' + postId)
        .then(res => res.json())
        .then(comments => {
            container.innerHTML = '';
            comments.forEach(c => appendCommentToContainer(container, c));
        })
        .catch(err => console.error("Ошибка загрузки:", err));
};

window.sendComment = function() {
    const input = document.getElementById('commentInput');
    const text = input.value.trim();
    if (!text) return;

    fetch('/api/comments', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ publicationId: window.currentPostId, content: text })
    })
        .then(res => res.json())
        .then(comment => {
            appendCommentToContainer(document.getElementById('commentsContainer'), comment);
            updateCounter(window.currentPostId, 1);
            input.value = '';
        });
};

window.appendCommentToContainer = function(container, c) {
    const author = c.authorLogin || 'Аноним';
    const isMyComment = (typeof currentUserLogin !== 'undefined' && author === currentUserLogin);

    const div = document.createElement('div');
    div.className = "mb-2 border-bottom pb-2 d-flex justify-content-between align-items-start";
    div.setAttribute('data-comment-id', c.id);
    div.innerHTML = `
        <div><strong class="text-primary">${author}:</strong> <span>${c.content}</span></div>
        ${isMyComment ? `<span class="text-danger ms-2" style="cursor:pointer" onclick="deleteComment(${c.id})">&times;</span>` : ''}
    `;
    container.appendChild(div);
};

window.deleteComment = function(id) {
    if (!confirm("Удалить?")) return;

    fetch('/api/comments/' + id, { method: 'DELETE' })
        .then(res => {
            if (res.ok) {
                document.querySelectorAll(`[data-comment-id="${id}"]`).forEach(el => el.remove());
                updateCounter(window.currentPostId, -1);
            }
        });
};

function updateCounter(pubId, delta) {
    const el = document.getElementById(`cnt-pub-${pubId}`);
    if (el) {
        let current = parseInt(el.innerText) || 0;
        el.innerText = Math.max(0, current + delta);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    document.body.addEventListener('click', function(e) {
        const btn = e.target.closest('.open-post-btn');
        if (btn) {
            openPostModal(btn.dataset.id, btn.dataset.image, btn.dataset.description, btn.dataset.author);
        }
    });

    document.querySelectorAll('.send-comment-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const pubId = this.dataset.id;
            const input = document.querySelector(`.comment-input[data-id="${pubId}"]`);
            const content = input.value.trim();
            if (!content) return;

            fetch('/api/comments', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ publicationId: pubId, content: content })
            })
                .then(res => res.json())
                .then(comment => {
                    const container = document.getElementById(`comments-pub-${pubId}`);
                    if (container) appendCommentToContainer(container, comment);
                    updateCounter(pubId, 1);
                    input.value = '';
                });
        });
    });
});