# RestAPI-Consumption
Consumo de Rest API  en Netbeans con Java. Rest API construida en Google Colab con Flask.

# Backend con Flask en Google Colab
URL: https://colab.research.google.com/drive/1IVVTkR4qpDc-uDyxS_IqFMO7owjrmBQc?usp=sharing

## Backend en Flask sin ORM

```java {.highlight .highlight-source-java .bg-black}
!pip install flask flask-ngrok pyngrok flask-mysqldb
```

 ```java {.highlight .highlight-source-java .bg-black}
from flask_ngrok import run_with_ngrok
from pyngrok import ngrok
from flask import Flask, request, jsonify
from flask_mysqldb import MySQL

ngrok.set_auth_token("2W4m2nleawZte2DPjtLmjlp9xOF_7jmDvCNnwPPYbgNJ8wXYP")

app = Flask(__name__)

DATABASE_SETTINGS = {
    'user': 'root',
    'password': 'un2JEBHWKyEugi39NalO',
    'host': 'containers-us-west-78.railway.app',
    'db': 'railway',
    'port': 7391
}

# Database configuration
app.config['MYSQL_USER'] = DATABASE_SETTINGS['user']
app.config['MYSQL_PASSWORD'] = DATABASE_SETTINGS['password']
app.config['MYSQL_DB'] = DATABASE_SETTINGS['db']
app.config['MYSQL_HOST'] = DATABASE_SETTINGS['host']
app.config['MYSQL_PORT'] = DATABASE_SETTINGS['port']
mysql = MySQL(app)

@app.route('/museos', methods=['GET'])
def get_museos():
    cursor = mysql.connection.cursor()
    cursor.execute("SELECT * FROM museo")
    result = cursor.fetchall()
    cursor.close()

    museos = [{'mu_id': museo[0], 'mu_nombre': museo[1]} for museo in result]
    return jsonify(museos)

@app.route('/museo', methods=['POST'])
def add_museo():
    data = request.json
    mu_id = data['mu_id']
    mu_nombre = data['mu_nombre']

    cursor = mysql.connection.cursor()
    cursor.execute("INSERT INTO museo (mu_id, mu_nombre) VALUES (%s, %s)", (mu_id, mu_nombre,))
    mysql.connection.commit()
    cursor.close()
    return jsonify({"status": "success", "message": "Museo added successfully!"})

@app.route('/museo/<int:mu_id>', methods=['GET'])
def get_museo(mu_id):
    cursor = mysql.connection.cursor()
    cursor.execute("SELECT * FROM museo WHERE mu_id=%s", (mu_id,))
    museo = cursor.fetchone()
    cursor.close()

    if museo:
        return jsonify({'mu_id': museo[0], 'mu_nombre': museo[1]})
    return jsonify({"status": "failed", "message": "Museo not found"}), 404

@app.route('/museo/<int:mu_id>', methods=['PUT'])
def update_museo(mu_id):
    data = request.json
    mu_nombre = data['mu_nombre']

    cursor = mysql.connection.cursor()
    cursor.execute("UPDATE museo SET mu_nombre=%s WHERE mu_id=%s", (mu_nombre, mu_id))
    mysql.connection.commit()
    cursor.close()
    return jsonify({"status": "success", "message": "Museo updated successfully!"})

@app.route('/museo/<int:mu_id>', methods=['DELETE'])
def delete_museo(mu_id):
    cursor = mysql.connection.cursor()
    cursor.execute("DELETE FROM museo WHERE mu_id=%s", (mu_id,))
    mysql.connection.commit()
    cursor.close()
    return jsonify({"status": "success", "message": "Museo deleted successfully!"})

if __name__ == '__main__':
    run_with_ngrok(app)
    app.run()
```

## Backend en Flask con ORM

```java {.highlight .highlight-source-java .bg-black}
!pip install flask-sqlalchemy pymysql
```

```java {.highlight .highlight-source-java .bg-black}
from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_ngrok import run_with_ngrok
from pyngrok import ngrok

ngrok.set_auth_token("2W4m2nleawZte2DPjtLmjlp9xOF_7jmDvCNnwPPYbgNJ8wXYP")

app = Flask(__name__)

DATABASE_SETTINGS = {
    'user': 'root',
    'password': 'un2JEBHWKyEugi39NalO',
    'host': 'containers-us-west-78.railway.app',
    'db': 'railway',
    'port': 7391
}

# Configuración de la base de datos
app.config['SQLALCHEMY_DATABASE_URI'] = f"mysql+pymysql://{DATABASE_SETTINGS['user']}:{DATABASE_SETTINGS['password']}@{DATABASE_SETTINGS['host']}:{DATABASE_SETTINGS['port']}/{DATABASE_SETTINGS['db']}"

db = SQLAlchemy(app)

class Museo(db.Model):
    mu_id = db.Column(db.Integer, primary_key=True)
    mu_nombre = db.Column(db.String(100))

@app.route('/museos', methods=['GET'])
def get_museos():
    museos = Museo.query.all()
    return jsonify([{'mu_id': museo.mu_id, 'mu_nombre': museo.mu_nombre} for museo in museos])

@app.route('/museo', methods=['POST'])
def add_museo():
    data = request.json
    museo = Museo(mu_id=data['mu_id'], mu_nombre=data['mu_nombre'])
    db.session.add(museo)
    db.session.commit()
    return jsonify({"status": "success", "message": "Museo added successfully!"})

@app.route('/museo/<int:mu_id>', methods=['GET'])
def get_museo(mu_id):
    museo = Museo.query.get(mu_id)
    if museo:
        return jsonify({'mu_id': museo.mu_id, 'mu_nombre': museo.mu_nombre})
    return jsonify({"status": "failed", "message": "Museo not found"}), 404

@app.route('/museo/<int:mu_id>', methods=['PUT'])
def update_museo(mu_id):
    museo = Museo.query.get(mu_id)
    if museo:
        museo.mu_nombre = request.json['mu_nombre']
        db.session.commit()
        return jsonify({"status": "success", "message": "Museo updated successfully!"})
    return jsonify({"status": "failed", "message": "Museo not found"}), 404

@app.route('/museo/<int:mu_id>', methods=['DELETE'])
def delete_museo(mu_id):
    museo = Museo.query.get(mu_id)
    if museo:
        db.session.delete(museo)
        db.session.commit()
        return jsonify({"status": "success", "message": "Museo deleted successfully!"})
    return jsonify({"status": "failed", "message": "Museo not found"}), 404

if __name__ == '__main__':
    run_with_ngrok(app)
    app.run()
```
