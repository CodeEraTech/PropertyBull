package com.doomshell.property_bull.helper;

import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.MultipartTypedOutput;


/**
 * Created by Anuj on 2/16/2017.
 */

public interface Serverapi {
@FormUrlEncoded
    @POST("/mobile/user_registration")
    public void register_user(
        @Field("name") String name,
        @Field("lname") String lname,
        @Field("username") String username,
        @Field("landline") String landline,
        @Field("mobile") String mobile,
        @Field("city_id") String city_id,
        @Field("list_n") String list_n,
        @Field("list_p") String list_p,
        Callback<Response> callback);



@FormUrlEncoded
    @POST("/mobile/verify_otp")
    public  void verifyotp(
        @Field("mobile")String mobile,
        @Field("otp")String Otp,
    Callback<Response> callback);


    @FormUrlEncoded
    @POST("/mobile/viewsavesearch")
    public void viewsavesearch(
            @Field("id") String id,
            Callback<Response> callback);
    @FormUrlEncoded
    @POST("/mobile/delete_search")
    public void delete_search(
            @Field("id") String id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/delete_requirement")
    public void delete_requirement(
            @Field("id") String id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/delete_property")
    public void delete_property(
            @Field("id") String id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/user_login")
    public void loginuser(
            @Field("mobile") String mobile,
            Callback<Response> callback);

    @GET("/mobile/property_list")
    public void propertylist(
            Callback<Response> callback);


    @FormUrlEncoded
    @POST("/mobile/my_property")
    public void myproperty(
            @Field("id") String id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/single_project")
    public void single_recentproject(
            @Field("id") String id,
            Callback<Response> callback);


    @GET("/mobile/recent_properties")
    public void recent_properties(
            Callback<Response> callback);

    @GET("/mobile/recent_project")
    public void recent_project(
            Callback<Response> callback);

    @GET("/mobile/projectGallery")
    public void gallerys(
            Callback<Response> callback);

    @GET("/mobile/loanType")
    public void loan_type(
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/get_profile")
    public void get_profile(
            @Field("id")String id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/setContact_Details")
    public void set_detail(
            @Field("id")String id,
            @Field("isproperty_dealer")String pdealer,
            @Field("name")String name,
            @Field("mobile")String mobile,
            @Field("email")String email,
            Callback<Response> callback);


    @FormUrlEncoded
    @POST("/mobile/homeLoan")
    public  void loan_request(
            @Field("loanType")String cus_id,
            @Field("userType")String p_typeid,
            @Field("name")String address,
            @Field("mobile")String mobile,
            @Field("email")String email,
            @Field("location")String loc,
            @Field("dob")String dob,
            @Field("grossSalary")String grossSalary,
            @Field("monthlySalary")String monthlySalary,
            Callback<Response> callback);


    @FormUrlEncoded
    @POST("/mobile/verify_otp")
    public  void addproperty(
            @Field("cus_id")String cus_id,
            @Field("p_typeid")String p_typeid,
            @Field("otp")String address,
            @Field("otp")String mobile,
            @Field("otp")String email,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/add_requirement")
    public  void add_requirement(
            @Field("category")String category,
            @Field("cus_id")String cus_id,
            @Field("p_typeid")String p_typeid,
            @Field("city_id")String city_id,
            @Field("location_id")String location_id,
            @Field("max_area")String max_area,
            @Field("min_area")String min_area,
            @Field("min_room")String min_room,
            @Field("max_room")String max_room,
            @Field("min_budget")String min_budget,
            @Field("max_budget")String max_budget,
            @Field("unit")String unit,
            Callback<Response> callback);


    @FormUrlEncoded
    @POST("/mobile/recentprojectfeature")
    public void recentprojectfeature(
            @Field("id") String id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/similar_property")
    public void similar_property(
            @Field("id") String id,
            Callback<Response> callback);

    @GET("/mobile/project_month")
    public void project_month(
            Callback<Response> callback);

    @GET("/mobile/consltants")
    public void consltants(
            Callback<Response> callback);
    @FormUrlEncoded
    @POST("/mobile/consltants_post")
    public void consltants_post(
            @Field("c_id")String city_id,
            Callback<Response> callback);
    @FormUrlEncoded
    @POST("/mobile/fetch_requirement")
    public void view_requirement(
            @Field("id")String id,
            Callback<Response> callback);

    @GET("/mobile/property_face")
    public void propertyface(
            Callback<Response> callback);


    @GET("/mobile/faq")
    public void faq_fetch(
            Callback<Response> callback);

    @GET("/mobile/city_list")
    public void citylist(
            Callback<Response> callback);

    @GET("/mobile/project_list")
    public void project_list(
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/locality")
    public void locality(
            @Field("city_id")String city_id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/search")
    public void search(
            @Field("option")String option,
            @Field("city_id")String city_id,
            @Field("location_id")String location_id,
            @Field("name")String name,
            @Field("p_typeid")String p_typeid,
            @Field("minprice")String minprice,
            @Field("maxprice")String maxprice,
            @Field("rooms")String rooms,
            @Field("agebuilding")String agebuilding,
            @Field("minarea")String minarea,
            @Field("maxarea")String maxarea,
            @Field("userrole")String userrole,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/mobile/savesearch")
    public void savesearch(
            @Field("data[Advance][category]")String option,
            @Field("data[Advance][p_typeid]")String p_typeid,
            @Field("data[Advance][state_id]")String state_id,
            @Field("data[Advance][min_price]")String min_price,
            @Field("data[Advance][max_price]")String max_price,
            @Field("data[Advance][city_id]")String city_id,
            @Field("data[Advance][min_area]")String min_area,
            @Field("data[Advance][max_area]")String max_area,
            @Field("data[Advance][unit]")String unit,
            @Field("data[Advance][age]")String age,
            @Field("data[Advance][min_room]")String min_room,
            @Field("data[Advance][max_room]")String max_room,
            @Field("data[Advance][min_floor]")String min_floor,
            @Field("data[Advance][max_floor]")String max_floor,
            @Field("data[Advance][owner]")String owner,
            @Field("data[Advance][faceid]")String faceid,
            @Field("data[Advance][location_id]")String location_id,
            @Field("data[Advance][description]")String description,
            @Field("data[Advance][cus_id]")String cus_id,
            @Field("data[Advance][title]")String title,
            Callback<Response> callback);


    @POST("/mobile/add_property")
    public void post_property(
            @Body MultipartTypedOutput file,
            Callback<Response> callback);

    @Multipart
    @POST("/mobile/add_property")
    public void post_propertydummy(
            @Part("option") RequestBody option,
            @Part("cus_id") RequestBody cus_id,
            @Part("p_typeid") RequestBody p_typeid,
            @Part("name") RequestBody name,
            @Part("city_id") RequestBody city_id,
            @Part("location_id") RequestBody location_id,
            @Part("address") RequestBody address,
            @Part("address2") RequestBody address2,
            @Part("pincode") RequestBody pincode,
            @Part("floor") RequestBody floor,
            @Part("p_floor") RequestBody p_floor,
            @Part("flooring") RequestBody flooring,
            @Part("faceid") RequestBody faceid,
            @Part("area") RequestBody area,
            @Part("per_unit") RequestBody per_unit,
            @Part("tot_price") RequestBody tot_price,
            @Part("room") RequestBody room,
            @retrofit2.http.Part ArrayList<MultipartBody.Part> file,
            Callback<Response> callback);

}
