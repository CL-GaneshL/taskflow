
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Update Hourly Cost</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body">

        <!-- ================================================== -->
        <!-- - new team form                                    -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="HourlyCostForm" >

            <div class="form-group">

                <label class="control-label padding-bottom-10"> 
                    New Hourly Cost :
                </label>

                <span class="input-icon">

                    <i class="fa fa-rupee"></i>

                    <input type="text" 
                           class="form-control"
                           name="newHourlyCost"
                           ng-model ="newHourlyCost"
                           ng-init ="newHourlyCost"
                           required
                           />

                </span> 

            </div>

        </form>

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="updateHourlyCost()">
            Update
        </button>

    </div>

</div>