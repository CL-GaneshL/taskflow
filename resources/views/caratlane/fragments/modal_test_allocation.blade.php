
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Test Allocation</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body">

        <form class="form-horizontal padding-bottom-0" role="form">

            <fieldset>

                <legend>
                    Please check the following messages :
                </legend>

                <!-- ================================================== -->
                <!-- - logs                                             -->
                <!-- ================================================== -->
                <ul class="timeline-xs col-md-12" ng-repeat="log in logs">

                    <li ng-class="(log.type === 'ERROR') ? 'alert-danger' : ((log.type === 'WARNING') ? 'alert-warning' : '')">
                        <small>  
                            <span ng-class="(log.type === 'INFO') ? 'fa fa-carret-rigth' : ''"></span> 
                            <span ng-class="(log.type === 'WARNING') ? 'fa fa-warning' : ''"></span> 
                            <span ng-class="(log.type === 'ERROR') ? 'fa fa-times' : ''"></span> 
                            <% log.msg %>
                        </small>
                    </li>

                </ul>

                <ul class="timeline-xs col-md-12 padding-left-10 padding-top-10">

                    <li ng-class="(results.error > 0) ? 'alert-danger' : ((results.error === 0) && (results.warning > 0) ? 'alert-warning' : 'alert-success')">
                        <strong>
                            <span ng-class="(results.warning === 0) && (results.error === 0) ? 'fa fa-thumbs-o-up' : ''"></span> 
                            <span ng-class="(results.warning > 0) && (results.error === 0) ? 'fa fa-hand-o-right' : ''"></span> 
                            <span ng-class="(results.error > 0) ? 'fa fa-hand-stop-o' : ''"></span>                             
                            <% results.msg %> 
                        </strong>  
                    </li>

                </ul>

            </fieldset>

        </form>
        <!-- ================================================== -->

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Ok
        </button>

    </div>

</div>