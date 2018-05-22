package sx.importacion

class ImportadorDeCalle4Job {

    def importadorDeExistencias
    def importadorDeClientes
    def importadorDeVales
    def importadorDeTraslados

      static triggers = {
      cron name:   'impCalle4',   startDelay: 20000, cronExpression: '0 0/4 * * * ?'
      }

      def execute() {

       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Calle 4  ${new Date()}               *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'CALLE 4'
       try{
      //    println "importando Vales: "+sucursal
          importadorDeVales.importarSucursal(sucursal)
       }catch (Exception e){
              e.printStackTrace()
      }
      try{
      //   println "importando Traslados: "+sucursal
         importadorDeTraslados.importarSucursal(sucursal)
      }catch (Exception e){
             e.printStackTrace()
     }

      }
}
