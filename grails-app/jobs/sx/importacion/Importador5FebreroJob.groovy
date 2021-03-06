package sx.importacion

class Importador5FebreroJob {

    def importadorDeExistencias
    def importadorDeClientes
    def importadorDeVales
    def importadorDeTraslados


      static triggers = {
      cron name:   'imp5Febrero2',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {

       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando 5 Febrero Dos   ${new Date()}            *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'CF5FEBRERO'

       try{
         //println "importando Existencias: "+sucursal
          importadorDeExistencias.importar(sucursal)
       }catch (Exception e){
              e.printStackTrace()
      }

      try{
        // println "importando Clientes: "+sucursal
         importadorDeClientes.importar(sucursal)
      }catch (Exception e){
             e.printStackTrace()
     }
     try{
    //    println "importando ComunicacionEmpresa: "+sucursal
        importadorDeClientes.importarComunicacionEmpresa(sucursal)
     }catch (Exception e){
            e.printStackTrace()
    }

      }
}
