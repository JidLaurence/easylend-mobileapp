package com.example.myapplication;

import java.util.ArrayList;

public class Model {
    Boolean companyStatus;
    Boolean status;
    String message;
    String _id;
    String email;
    String password;
    String firstname;
    String middlename;
    String lastname;
    String birthday;
    String phone_number;
    Boolean isUpdated;
    Boolean isVerified;

    String company_id;
    String company_name;

    ArrayList<data> data;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }

    public Boolean getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(Boolean companyStatus) {
        this.companyStatus = companyStatus;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public ArrayList<Model.data> getData() {
        return data;
    }

    public void setData(ArrayList<Model.data> data) {
        this.data = data;
    }

    public class data{
        String _id;
        String customer_id;
        String company_id;
        String branch_id;
        String staff_id;
        String collect_dates_id;
        String email;
        String firstname;
        String middlename;
        String lastname;
        String phone_number;
        String address;
        String barangay;
        String city;
        String province;
        String region;
        Integer capital;
        Integer months;
        Integer type;
        Integer interest;
        Integer total_payed;
        Integer balance;
        Integer collect;
        Integer capital_total;
        Integer capital_interest;
        Integer amount;
        Integer month;
        Integer day;
        Integer year;
        Boolean isPaid;
        Boolean lapses;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getCollect_dates_id() {
            return collect_dates_id;
        }

        public void setCollect_dates_id(String collect_dates_id) {
            this.collect_dates_id = collect_dates_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getMiddlename() {
            return middlename;
        }

        public void setMiddlename(String middlename) {
            this.middlename = middlename;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBarangay() {
            return barangay;
        }

        public void setBarangay(String barangay) {
            this.barangay = barangay;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public Integer getCapital() {
            return capital;
        }

        public void setCapital(Integer capital) {
            this.capital = capital;
        }

        public Integer getMonths() {
            return months;
        }

        public void setMonths(Integer months) {
            this.months = months;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getInterest() {
            return interest;
        }

        public void setInterest(Integer interest) {
            this.interest = interest;
        }

        public Integer getTotal_payed() {
            return total_payed;
        }

        public void setTotal_payed(Integer total_payed) {
            this.total_payed = total_payed;
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        public Integer getCollect() {
            return collect;
        }

        public void setCollect(Integer collect) {
            this.collect = collect;
        }

        public Integer getCapital_total() {
            return capital_total;
        }

        public void setCapital_total(Integer capital_total) {
            this.capital_total = capital_total;
        }

        public Integer getCapital_interest() {
            return capital_interest;
        }

        public void setCapital_interest(Integer capital_interest) {
            this.capital_interest = capital_interest;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Boolean getPaid() {
            return isPaid;
        }

        public void setPaid(Boolean paid) {
            isPaid = paid;
        }

        public Boolean getLapses() {
            return lapses;
        }

        public void setLapses(Boolean lapses) {
            this.lapses = lapses;
        }
    }
}