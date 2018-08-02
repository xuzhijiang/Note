url字符串不以(forward splash)/开头，但要以slash(/)结尾,
e.g articles/2003/
articles/2018/ -> articles/<int:year>/
通过<>提取变量作为作为处理函数的参数

```python
urlpatterns = [
    re_path('articles/(?P<year>[0-9]{4})/(?P<month>[0-9]{2})/(?P<msg>[\w-_]+)/'),\
    views.article_detail),
]
articles/2018/04/learn-django-mooc
views.article_detail(request, year="2018", month="04", msg="learn-django-mooc")
re_path提取变量只能为字符串的形式
```

路径去重
```python
urlpatterns = [
    path('<page_slug>-<page_id>/discuss/', views.discuss),
    path('<page_slug>-<page_id>/post/', views.post),
    path('<page_slug>-<page_id>/comment/, view.comment),
    path('<page_slug>-<page_id>/history/, view.history),
]
urlpatterns = [
    path('<page_slug>-<page_id>/', include([
        path('discuss/', views.discuss),
        path('post/', views.post),
        path('comment/, view.comment),
        path('history/, view.history),
    ])),
]

```

视图函数没法判断http请求类型，通过decorators在视图函数前进行判断

```python
@require_http_method(['GET', 'POST'])
def my_view(request):
    # I can assume now that only GET or POST requets make it this far
    # ...
    pass

```

#### stringfilter

[reference](https://docs.djangoproject.com/en/2.0/howto/custom-template-tags/#django.template.defaultfilters.stringfilter)
