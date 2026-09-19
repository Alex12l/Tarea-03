const express = require('express');          
const mysql = require('mysql2');           
const bodyParser = require('body-parser');  
const PORT = 3000; 

const app = express();
app.use(bodyParser.json()); 

const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'cafeteria_db'
});

//Verificar la conexión a la BD
db.connect((err) => {
  if (err) throw err;
  console.log('Conectado a la base de datos cafeteria_db');
});

//Listar Bebidas
app.get('/productos', (req, res) => {
  const sql = `
  SELECT id, nombre, tamanio, precio, disponible 
  FROM bebidas 
    ORDER BY id DESC 
    LIMIT 20;
  `;

  db.query(sql, (err, result) => {
    if (err) return res.status(500).send(err);
    res.json(result);
  });
});

app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
});



// Eliminar Bebida
app.delete('/productos/:id', (require, result) => {
  const { id } = require.params;
  const sql = `DELETE FROM bebidas WHERE id = ?`;
  
  db.query(sql, [id], (err, res) => {
    if (err) return result.status(500).send(err);
    if (res.affectedRows == 0) {
      return result.status(404).send({ message: "No encontrado" });
    }

    return result.send({ message: 'Eliminado correctamente' });
  });
});

//Iniciando servidor del WebService (JS)
app.listen(PORT, () => {
  console.log(`Servidor iniciado en http://localhost:${PORT}`);
});