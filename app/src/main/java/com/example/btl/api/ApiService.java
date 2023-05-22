package com.example.btl.api;

import com.example.btl.model.AddCart;
import com.example.btl.model.BookAll;
import com.example.btl.model.BookDetail;
import com.example.btl.model.GetBill;
import com.example.btl.model.GetCart;
import com.example.btl.model.GetOrder;
import com.example.btl.model.Order;
import com.example.btl.model.UpdateCart;
import com.example.btl.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String BASE_URL = "http:10.0.2.2:4000";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    //localhost:4000/cart
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);

    //get all book
    @GET("/product")
    Call<BookAll> getAllBook();

    //get book sap sap theo rate
    @GET("/product/rate")
    Call<BookAll> getBookRate();
    // get book theo cate với so lượng cần get
    @GET("/product/category/{cat}/{pageSize}")
    Call<BookAll> getBookCat(@Path("cat") String cat, @Path("pageSize") int pageSize);

    // get book theo id
    @GET("/product/{productId}")
    Call<BookDetail> getBookId(@Path("productId") String productId);

    //get book theo name
    @GET("/product/name/{productName}")
    Call<BookAll> getBookByName(@Path("productName") String productName);

    // get giỏ hàng theo userId
    @GET("/cart/{UserId}")
    Call<GetCart> getCart(@Path("UserId") String UserId);

    // get Oder theo userId
    @GET("/order/{UserId}")
    Call<GetBill> getOrder(@Path("UserId") String UserId);

    // update cart (tăng sản phẩm, giảm sản phẩm)
    @PUT("/cart")
    Call<GetCart> updateCart(@Body UpdateCart updateCart);

    // thêm sản phẩm vào giỏ hàng
    @POST("/cart")
    Call<GetCart> addCart(@Body AddCart addCart);
    // xóa sản phẩm trong giỏ hàng theo id
    @DELETE("/cart/{UserId}/{productId}")
    Call<GetCart> deleteProduct(@Path("UserId") String UserId, @Path("productId") String productId);

    // thanh toán và thêm sản phẩm vào trong order
    @POST("/order")
    Call<GetOrder> order(@Body Order order);

    // tìm bill theo ngày
    @GET("/order/date/{date}")
    Call<GetBill> getOrderbyDate(@Path("date") String date);
}
