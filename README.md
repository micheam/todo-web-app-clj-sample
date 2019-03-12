# Todo Web App with clojure

簡単なタスク管理WEBアプリケーションの実装サンプルです。

以下の構成で実装されています。

- clojure
- compojure
- java.jdbc
- postgres

## 仕様

**タスクの一覧取得**
```rest
GET /api/
GET /api/?limit=10
```

**タスクの登録**
```rest
POST /api/
{
  "title": "awesome task",
  "link": "http://example.com/detail/of/awesome/task",
  "due_date": "2019-02-09T00:00:00Z",
  "done": false
}

```

**タスクの取得**
```rest
GET /api/:task_id
```

**タスクの更新**
```rest
PATCH /api/:task_id
{
  "title": "updated my awsome task",
  "done": true
}
```

**タスクの削除**
```rest
DELETE /api/:task_id
```


APIの詳細仕様については、[api-spec](api-spec.rest) を参照してください。

## ローカル実行方法

[Leiningen][] 2.0.0 以上がインストールされている必要があります。
[leiningen]: https://github.com/technomancy/leiningen

WEBサーバを起動する場合には、以下を実行してください :

```sh
$ lein ring server
```

## Heroku環境
以下にデプロイされています。  
https://todo-web-qpp-clj-sample.herokuapp.com

実際にAPIの動作を確認してみる場合には、
以下の様にアクセスしてみてください:

```sh
$ curl -X POST \
  -d '{"title","Hello, World."}' \
  https://todo-web-qpp-clj-sample.herokuapp.com/api
```

Herokuにアカウントをお持ちの場合は、
以下のボタン使って自身のHeroku環境にデプロイすることもできます。

<a href="https://heroku.com/deploy?template=https://github.com/micheam/todo-web-app-clj-sample">
  <img src="https://www.herokucdn.com/deploy/button.svg" alt="Deploy">
</a>

## 参考
[Building a Database-Backed Clojure Web Application - devcenter.heroku.com](https://devcenter.heroku.com/articles/clojure-web-application)

## License

Copyright © 2019 michito.maeda+github@gmail.com
