# ContextLoaderListener的作用

    注意基于注解的springmvc中,ContextLoaderListener不是用于创建根容器的(ROOT WebApplicationContext)，
    而是用于创建根容器中要存放service和repository这些bean组件的.然后把这些bean组件添加到父容器中.
    
    基于xml的springmvc中,ContextLoaderListener是用于创建根容器的(ROOT WebApplicationContext),
    以及用于创建根容器中要存放service和repository这些bean组件的.然后把这些bean组件添加到父容器中.
