<!-- ============================================================= -->
<!-- -- authenticated user role and privileges                     -->
<!-- ============================================================= -->

@inject('settings', 'App\Helpers\SettingsHelper')

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->   
<div class="container-fluid container-fullw bg-white no-border">

    <div class="row col-md-12">

            {{ $settings->getLaravelLogFileContent() }}

    </div>

</div>

