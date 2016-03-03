
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Change Password</h3>
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
              name="passwordForm" >

            <div class="form-group">
                <label class="control-label"> 
                    Password :
                </label>
                <input type="text" 
                       class="form-control"
                       name="newPassword"
                       ng-model ="newPassword"
                       placeholder ="Enter a Password"
                       required
                       />
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

        <button class="btn btn-primary btn-o" ng-click="changePassword()">
            Change
        </button>

    </div>

</div>