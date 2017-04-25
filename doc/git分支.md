#git分支#
1.创建分支  
git checkout -b dev  

2.切换分支  
git checkout dev

3.修改dev分支  

4.切换到master分支  
git checkout master

5.将dev分支修改的内容合并到master中  
git merge dev

6.删掉dev分支  
git branch -d dev

7.比较分支
git diff dev master

8.提交master分支
git push origin master



git reset HEAD 如果后面什么都不跟的话 就是上一次add 里面的全部撤销了
git log 查看节点   
commit xxxxxxxxxxxxxxxxxxxxxxxxxx 
git reset commit_id