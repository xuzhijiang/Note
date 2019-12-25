# git常用命令

```shell
# 工作目录    ->     暂存区      ->      本地仓库      ->      远程仓库

# 撤销暂存区的内容(从暂存区撤销到工作区),即由绿色变成红色字体
git reset HEAD <文件>

# 丢弃工作区的改动(颜色由红色变成没有文件被改动)
git checkout -- <文件>

# 把工作区修改提交到暂存区
git add <文件>

# 将指针移动到commit-id(HEAD指向commit-id),将commit-id之后的修改
# 只保留在工作目录(只有红色字体)
git reset --mixed <commit-id>

# 将指针移动到commit-id(HEAD指向commit-id).
# 保留commit-id之后的修改到暂存区(只有绿色字体)
git reset --soft <commit-id>

# 将指针移动到commit-id(HEAD指向commit-id).
# commit-id之后的修改都会被遗弃(使用的时候要注意)
git reset --hard <commit-id>

# git stash可以保存目前的工作目录和暂存区返回到干净的工作空间
git stash save "push to stash area"

# stash(藏，窝) 相当于一个栈，查看这个栈里面保存的内容
git stash list

# 把某一个点的内容还原到工作目录和暂存区
git stash apply stash@{0}

# 删除stash栈中的某一个点
# 由于已经恢复了工作状态，可以把之前记录的对应的stash状态删除掉
git stash drop stash@{0}

# stash pop = stash apply + stash drop(捷径)
# 把某一个点的内容还原到工作目录和暂存区并且把这个点从stash栈中删除
git stash pop stash@{0}

# 查看某文件的修改记录
git log -p <file>

创建一个分支: `git branch <branchName>`

删除: `git branch -D <branchName>`

显示分支信息: git branch -v

创建一个新分支并且切换到此分支: git checkout -b <branchname>

git checkout <reference>(commit id)

git checkout -b issue-26

删除新添加的文件和文件夹: git clean -df

git fetch 获取远程仓库的提交历史

查看修改的文件: git whatchanged

git whatchanged fileName

git show commitid --stat

git show commit-id filename

仅从暂存区删除: `git rm --cached filename`

从暂存区和工作目录删除: `git rm -f filename`

`git log --pretty=oneline`

`git log --oneline`

# 配置用户名(--global: Setting your email address for every repository on your computer)
git config --global user.name "John Doe"

# 配置邮箱
git config --global user.email johndoe@example.com

# 查看用户名(without --global: for a single repository)
git config user.name

# 查看邮箱
git config --global user.email

# You can view all of your settings and 
# where they are coming from using:
# 查看git的配置(email,username等,以及在哪里配置)
git config --list --show-origin
```

### merge

git merge --no-ff origin/develop

----进入master下，会将develop的修改更新到master上

git merge --no-ff origin/master

----进入develop下，会将master的修改更新到develop上

git merge <branch a>:

合并a分支上的内容到当前分支上,遇到冲突需手动解决

git merge next --no-ff not fast forward:

不要使用fast forward的方式进行合并

有种情况不希望fast-forward,因为merge有一个分叉产生，可以告诉我们在这个节点上，我们发生了合并，在产品开发中，当在feature分支合并到development分支时，我们是需要知道这次合并的，因为这样我们就可以知道这次合并在哪个节点发生，就可以git merge next --no-ff

git pull =git fetch + git merge

#### 为什么大部分情况下，git fetch 要优于直接使用 git pull?

    不难发现， 课程中对于可能常用的 git pull 命令着墨不多.  而把大量的时间放在了 git fetch + git merge 的工作原理上,git fetch是把有冲突的代码拉取下来，不会自动合并，需要手动进行合并，可以看到冲突的具体细节。
    而git pull是自动合并。看不到具体细节，然而合并完后有问题，却找不到具体问题在哪。
    所以说大部分情况下，git fetch 要优于直接使用 git pull。

### rebase

feature上的62ecb3合并到master分支

```shell
git checkout master
git cherry-pick 62ecb3
```

合并feature分支的76cada ~ 62ecb3到master分支。

1. 基于feature创建一个新的newbranch分支，并指明newbranch分支的最后一个commit
2. rebase这个newbranch分支的提交到master，76cada^指明你想从哪个特定的commit开始。
3. 结果就是feature分支的76cada ~ 62ecb3都被合并到了master分支。

```shell
git checkout -b newbranch 62ecb3
git rebase --onto master 76cada^
```

git rebase是重演，而不是复制,这样提交历史就变的线性了，就类似于fast-forward

不需要把feature上的所有的提交在master上重演,可以自己挑选需要重演的那个节点到master分支上: `git rebase --onto master 5751363`

千万不要在公有分支上使用rebase，master分支就是一个例子.
