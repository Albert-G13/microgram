document.addEventListener('DOMContentLoaded', function() {
    const likeButtons = document.querySelectorAll('.like-btn');

    likeButtons.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            const pubId = this.getAttribute('data-id');
            const countSpan = this.querySelector('.likes-count');

            fetch(`/api/likes/${pubId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) return response.json();
                    if (response.status === 403) alert("Нужно авторизоваться!");
                })
                .then(newCount => {
                    if (newCount !== undefined) {
                        countSpan.innerText = newCount;
                        this.classList.toggle('text-danger');
                    }
                })
                .catch(err => console.error('Ошибка:', err));
        });
    });
});