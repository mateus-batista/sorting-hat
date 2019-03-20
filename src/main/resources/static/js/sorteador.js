const sortear = () => {

    let bridge = [
        {sala: "101", bridgers: ["deborawernke@bridge.ufsc.br","delrobson@bridge.ufsc.br","suleiman@bridge.ufsc.br","gustavolobo@bridge.ufsc.br","jades@bridge.ufsc.br","celio@bridge.ufsc.br"]},
        {sala: "108", bridgers: ["ewerton@bridge.ufsc.br","burigo@bridge.ufsc.br","wag@bridge.ufsc.br","paulo@bridge.ufsc.br"]},
        {sala: "109", bridgers: ["rafael@bridge.ufsc.br","amanda.aquino@bridge.ufsc.br","fernando@bridge.ufsc.br","leonardo.phildeg@bridge.ufsc.br","leticia@bridge.ufsc.br","carlosrebelato@bridge.ufsc.br","saldanha@bridge.ufsc.br","anna.victoria@bridge.ufsc.br"]},
        {sala: "303", bridgers: ["ricardo@bridge.ufsc.br","fernanda.mioto@bridge.ufsc.br","lucasrosaj@bridge.ufsc.br","rahony@bridge.ufsc.br","fabricio.matos@bridge.ufsc.br","otavioribeiro@bridge.ufsc.br","jobe@bridge.ufsc.br","diogo@bridge.ufsc.br","juliana@bridge.ufsc.br","leonardoschluter@bridge.ufsc.br"]},
        {sala: "304", bridgers: ["fabiosr@bridge.ufsc.br","nathanwerlich@bridge.ufsc.br","fabiopreisler@bridge.ufsc.br","mirian@bridge.ufsc.br","monique@bridge.ufsc.br","silvia@bridge.ufsc.br","angelo@bridge.ufsc.br","chris@bridge.ufsc.br","gessica@bridge.ufsc.br"]},
        {sala: "305", bridgers: ["isabellepinheiro@bridge.ufsc.br","taha@bridge.ufsc.br","giselle.nascimento@bridge.ufsc.br","odilon@bridge.ufsc.br","larissataw@bridge.ufsc.br","gustavomoser@bridge.ufsc.br","thaisgoulartf@bridge.ufsc.br","bruno.luiz@bridge.ufsc.br"]},
        {sala: "306", bridgers: ["postal@bridge.ufsc.br","cristiano@bridge.ufsc.br","bruno@bridge.ufsc.br","joaomiguel@bridge.ufsc.br","renan@bridge.ufsc.br","ana.elisa@bridge.ufsc.br","leo.soares@bridge.ufsc.br","ja.pierry@bridge.ufsc.br","caio@bridge.ufsc.br"]},
        {sala: "307", bridgers: ["isaac@bridge.ufsc.br","nathan@bridge.ufsc.br","petroski@bridge.ufsc.br","lucas.joao@bridge.ufsc.br","candinho@bridge.ufsc.br","jessica@bridge.ufsc.br","davi@bridge.ufsc.br","allan@bridge.ufsc.br","luis.munoz@bridge.ufsc.br"]},
        {sala: "308", bridgers: ["kanarek@bridge.ufsc.br","salomao@bridge.ufsc.br","nathalia@bridge.ufsc.br","lucas.suppes@bridge.ufsc.br"]},
        {sala: "609", bridgers: ["camila@bridge.ufsc.br","geovana@bridge.ufsc.br","nathalia.lucca@bridge.ufsc.br","rafa@bridge.ufsc.br","christian@bridge.ufsc.br","marcel@bridge.ufsc.br"]},
    ]

    const casas = [
        {id: 1, nome: "Grifinória", alunos: [], numeroAlunos: 0},
        {id: 2, nome: "Lufa-Lufa", alunos: [], numeroAlunos: 0},
        {id: 3, nome: "Corvinal", alunos: [], numeroAlunos: 0},
        {id: 4, nome: "Sonserina", alunos: [], numeroAlunos: 0},
    ]

    const numeroMinimo = 3
    const numeroSorteados = 2

    //Fisher-Yates (aka Knuth) Shuffle algorithm
    const shuffle = (array) => {

        let currentIndex = array.length;
        let temporaryValue, randomIndex;

        while (0 !== currentIndex) {
            randomIndex = Math.floor(Math.random() * currentIndex);
            currentIndex -= 1;

            temporaryValue = array[currentIndex];
            array[currentIndex] = array[randomIndex];
            array[randomIndex] = temporaryValue
        }

        return array;
    }

    let casaAtual = 0
    while (bridge.length > 0){
        const salas = shuffle(bridge.map(s => s.sala))
        while (salas.length > 0) {
            const salaSorteada = bridge.map(b => b.sala).indexOf(salas.pop())
            let bridgers = shuffle(bridge[salaSorteada].bridgers)
            if (bridgers.length < (numeroMinimo+1)){
                bridgers.splice(0,bridgers.length).forEach(bridger => {
                    casas[casaAtual].alunos.push({nome: bridger, sala: bridge[salaSorteada].sala})
                })
                casaAtual++
            }else {
                bridgers.splice(0, numeroSorteados).forEach(bridger => {
                    casas[casaAtual].alunos.push({nome: bridger, sala: bridge[salaSorteada].sala})
                })
                casaAtual++
            }
            if (bridgers.length === 0){
                bridge.splice(salaSorteada, 1)
            }
            if (casaAtual === casas.length ){
                casaAtual = 0
            }
        }
    }
    casas.map(casa => casa.numeroAlunos = casa.alunos.length)
    let sql = "insert into aluno (email, id_casa) values"
    casas.forEach(casa => {
        casa.alunos.forEach( aluno =>  sql+="\n ('"+aluno.nome+"',"+casa.id+"),")
    })
    const last = sql.lastIndexOf(",")
    sql = sql.substring(0, last)+";"
    document.getElementById("json").innerHTML = JSON.stringify(casas, undefined, 2)
    document.getElementById("sql").innerHTML = sql

}

sortear()