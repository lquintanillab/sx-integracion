package sx.exportacion

class ExportadorCalle4Job {

    def exportadorDeClientes
    def exportadorDeClientesCredito
    def exportadorDeProductos
    def exportadorDeExistencia
    def exportadorDeVales
    def exportadorDeTraslados

      static triggers = {
      cron name:   'expCalle42',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {

        println "************************************************************"
        println "*                                                          *"
        println "*                    Exportando Calle 4 Dos                *"
        println "*                                                          *"
        println "************************************************************"
        def sucursal = 'CALLE 4'

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
