package sx

class ImportadorDeDevolucionesJob {

def importadorDeDevoluciones

    static triggers = {
       cron name:   'impDevoluciones',   startDelay: 10000, cronExpression: '0 30 19 * * ?'
    }

    def execute() {
        // execute job
    }
}
