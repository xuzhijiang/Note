### Git

仅从暂存区删除: `git rm --cached filename`

从暂存区和workdir删除: `git rm -f filename`

git commit -a -m "full commit"

美化log输出: 

`git log --pretty=oneline`

`git log --oneline`

master分支的内容会覆盖缓存区和工作区的相应文件，工作区和缓存区未提交的内容会丢失，慎重！(一般不要用): `git checkout HEAD -- <filename>`

查看指定文件的提交历史: `git log -p <file>`

#### 分支操作

创建一个分支: `git branch <branchName>`

删除: `git branch -d <branchName>`

显示左右的分支信息: `git branch -v`

git checkout <branchname>

git checkout -b <branchname>

git checkout <reference>(commit id)

git checkout -b issue-26

git reset --mixed <commit-id

移动到commit-id的提交,将当前的 内容复制到暂存区

git reset --soft <commit-id>

移动到commit-id的提交.工作目录和暂存区不会有任何变化，仅仅只是指针发生了改变

git reset --hard <commit-id>

移动到commit-id的提交,将当前的 内容复制到暂存区和工作目录

git reset HEAD <filename> (撤销暂存区的内容)

git stash : 保存目前的工作目录和暂存区返回到干净的工作空间

git stash save "push to stash area"

stash 相当于一个栈

git stash list

git stash apply stash@{0}

git stash drop stash@{0} 由于已经恢复了工作状态，可以把之前记录的对应的stash状态删除掉

stash pop = stash apply + stash drop(捷径)

git merge <branch a>:

合并a分支上的内容到当前分支上,遇到冲突需手动解决

git cat-file -p HEAD 显示某一个具体对象的信息

git merge next --no-ff not fast forward:

不要使用fast forward的方式进行合并

>   有种情况不希望fast-forward,因为merge有一个分叉产     生，可以告诉我们在这个节点上，我们
    发生了合并，在产品开发中，当在feature分支合并到development分支时，我们是需要知道这次合并的，
    因为这样我们就可以知道这次合并在哪个节点发生，就可以git merge next --no-ff

git rebase是重演，而不是复制

这样提交历史就变的线性了，就类似于fast-forward

git rebase --onto:

(不需要把feature上的所有的提交在master上重演

就可以自己挑选需要重演的那个节点到master分支上:

git rebase --onto master 5751363

千万不要在公有分支上使用rebase，master分支就是一个例子.

git tag 对某一个提交设置一个不变的别名

git tag v0.1 7632121

git checkout v0.1

git tag --list

git remote -v查看远程仓库的信息

一般会把默认远程分支的别名取为origin

git push origin master

git fetch 获取远程仓库的提交历史

git fetch origin master

git merge origin/master

git pull =git fetch + git merge

#### 为什么大部分情况下，git fetch 要优于直接使用 git pull?

    不难发现， 课程中对于可能常用的 git pull 命令着墨不多.  而把大量的时间放在了 git fetch + git merge 的工作原理上,git fetch是把有冲突的代码拉取下来，不会自动合并，需要手动进行合并，可以看到冲突的具体细节。
    而git pull是自动合并。看不到具体细节，然而合并完后有问题，却找不到具体问题在哪。
    所以说大部分情况下，git fetch 要优于直接使用 git pull。

git fetch --all

git merge --no-ff origin/develop

----进入master下，会将develop的修改更新到master上

git merge --no-ff origin/master

----进入develop下，会将master的修改更新到develop上

git whatchanged 每次修改的文件列表

git whatchanged fileName

git show commitid --stat

git show 某次的提交哈希值 文件名(查看某次提交某个文件的变化)

#### merge and rebase

feature上的62ecb3合并到master分支
```shell
git checkout master
git cherry-pick 62ecb3
```

> 假设你需要合并feature分支的commit 76cada ~62ecb3 到master分支。
首先需要基于feature创建一个新的分支，并指明新分支的最后一个commit,
然后，rebase这个新分支的commit到master（--ontomaster）。
76cada^ 指明你想从哪个特定的commit开始。
得到的结果就是feature分支的commit 76cada ~62ecb3 都被合并到了master分支。

```shell
git checkout -b newbranch 62ecb3
git rebase --onto master 76cada^
```

添加远程仓库别名: `git remote add origin ~/git-server`

