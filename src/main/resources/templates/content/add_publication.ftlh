<#import "../layout.ftlh" as layout>

<@layout.mainLayout title="Новая публикация">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h3 class="card-title mb-4">Создать публикацию</h3>

                    <form action="/publications/add" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="image" class="form-label">Выберите фото</label>
                            <input type="file" name="file" id="image" class="form-control" accept="image/*" required>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Описание</label>
                            <textarea name="description" id="description" class="form-control" rows="3" placeholder="Напишите что-нибудь..."></textarea>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary">Опубликовать</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@layout.mainLayout>