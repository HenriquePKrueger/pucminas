<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pesquisa de Prestadores</title>
</head>
<body>
    <h2>Filtrar Prestadores</h2>
    
    <form action="/pesquisar" method="GET">
        <label>Tipo de Serviço:</label>
        <select name="servico">
            <option value="encanamento">Encanamento</option>
            <option value="reparos">Reparos de Móveis</option>
        </select>

        <label>Localização:</label>
        <input type="text" name="localizacao" placeholder="Ex: Belo Horizonte">

        <label>Gênero:</label>
        <select name="genero">
            <option value="M">Masculino</option>
            <option value="F">Feminino</option>
        </select>

        <button type="submit">Pesquisar</button>
    </form>
</body>
</html>