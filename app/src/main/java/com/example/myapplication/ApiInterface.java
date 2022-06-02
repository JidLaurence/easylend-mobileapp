package com.example.myapplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/collector/login")
    Call<Model> getUserInformation(@Field("apiToken") String apiToken, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/collector/signup")
    Call<Model> signupUserInformation(@Field("apiToken") String apiToken, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/collector/update_code")
    Call<Model> updateCodeInformation(@Field("apiToken") String apiToken, @Field("_id") String _id, @Field("email_code") String email_code);

    @FormUrlEncoded
    @POST("/collector/accept_company")
    Call<Model> accept_companyInformation(@Field("apiToken") String apiToken, @Field("_id") String _id, @Field("company_id") String company_id, @Field("status") Boolean status);

    @FormUrlEncoded
    @POST("/collector/update_profile")
    Call<Model> updateUserInformation(@Field("apiToken") String apiToken,@Field("_id") String _id, @Field("firstname") String firstname, @Field("middlename") String middlename, @Field("lastname") String lastname, @Field("phone_number") String phone_number, @Field("password") String password);

    @FormUrlEncoded
    @POST("/collector/get_invitation")
    Call<Model> getCompanyInvitation(@Field("apiToken") String apiToken, @Field("_id") String _id);

    //ADD EMAIL AND PASSWORD FOR VALIDATION
    @FormUrlEncoded
    @POST("/collector/save_collected")
    Call<Model> saveCollected(
            @Field("apiToken") String apiToken,
            @Field("customerDB_id") String customerDB_id,
            @Field("company_id") String company_id,
            @Field("branch_id")  String branch_id,
            @Field("staff_id") String staff_id,
            @Field("collector_id") String collector_id,
            @Field("amount") Integer amount,
            @Field("month") Integer month,
            @Field("day") Integer day,
            @Field("year") Integer year
    );

    //ADD EMAIL AND PASSWORD FOR VALIDATION ARRAY
    @FormUrlEncoded
    @POST("/collector/save_collected")
    Call<Model> saveCollectedArray(
            @Field("data")ArrayList<String> data
            );

}
