package com.example.eatproductmanager.Common;

import com.example.eatproductmanager.Domain.CategoryDomain;
import com.example.eatproductmanager.Domain.FoodDomain;
import com.example.eatproductmanager.Domain.UserDomain;

import java.util.ArrayList;

public class Common {
    public static UserDomain currentUser;

    public static ArrayList<CategoryDomain> categoriesCommon = new ArrayList<>();

    public static ArrayList<FoodDomain> foodsCommon = new ArrayList<>();
}
