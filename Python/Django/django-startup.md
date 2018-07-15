url字符串不以(forward splash)/开头，但要以slash(/)结尾,e.g articles/2003/
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


### urls.py

```python

urlpatterns = [
    path('entry/', include('subapp.urls')),
]

### subapp.urls.py
urlpatterns = [
    path('sub/', views.sub),
    path('help', views.help),
]
http://1271.0.0.1:8080/entry/sub    -->views.sub
http://1271.0.0.1:8080/entry/help    -->views.help
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

根目录处理函数
http://127.0.0.1:8000/
urlpatterns = [
    path('', <根目录处理函数>),
    re_path('^$', <根目录处理函数>),# or, 二选一
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

### 模板语言

#### comment

> {# single line comment #}

> {% comment %}multiple line comment{% endcomment %}


#### filter
> {% name|过滤器 %} {% name|f1|f1 %}过滤器对变量的值进行修饰: lower, escape, linebreaks, date, length...{{ my_date|date:"Y-m-d" }}

"""DjangoBlog URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
Raw String
In Python, r'^$'  is a regular expression that matches an empty line.
This looks like a regular expression (regex) commonly used in Django URL configurations.
The 'r' in front tells Python the expression is a raw string.
In a raw string, escape sequences are not parsed. For example,
'\n' is a single newline character. But, r'\n' would be two characters:
a backslash and an 'n'.
"""