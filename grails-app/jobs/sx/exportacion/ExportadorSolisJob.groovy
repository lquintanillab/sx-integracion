package sx.exportacion

class ExportadorSolisJob {

    def exportadorDeClientes
    def exportadorDeClientesCredito
    def exportadorDeProductos
    def exportadorDeExistencia
    def exportadorDeVales
    def exportadorDeTraslados

      static triggers = {
        cron name:   'expSolis2',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {

        println "************************************************************"
        println "*                                                          *"
        println "*                    Exportando Solis Dos    ${new Date()}               *"
        println "*                                                          *"
        println "************************************************************"

          def sucursal = 'SOLIS'

            try{
               exportadorDeClientesCredito.exportar(sucursal)
            }catch (Exception e){
                   e.printStackTrace()
           }
           try{
              exportadorDeClientes.exportar(sucursal)
           }catch (Exception e){
                  e.printStackTrace()
            }
            try{
               exportadorDeProductos.exportar(sucursal)
            }catch (Exception e){
                   e.printStackTrace()
           }
           try{
              exportadorDeExistencia.exportar(sucursal)
           }catch (Exception e){
                  e.printStackTrace()
          }

      }
}
