package com.inn.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.inn.core.utils.ConstantValueUtil.ResponseStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;

import javax.persistence.Basic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {

    @JsonView(Basic.class)
    private Object data;
    @JsonView(Basic.class)
    private ResponseStatus status;
    @JsonView(Basic.class)
    private Integer statusCode;
    @JsonView(Basic.class)
    private String contentType;
    @JsonView(Basic.class)
    private Integer contentLength;
    private Integer previousPage;
    private Integer currentPage;
    private Integer nextPage;
    private Integer currentPageCount;
    private Integer page;
    private Integer pageSize;
    private Integer totalCount;
    private Integer totalPage;

    private GenericResponse(Builder builder) {
        this.data = builder.data;
        this.status = builder.status;
        this.statusCode = builder.statusCode;
        this.contentType = builder.contentType;
        this.contentLength = builder.contentLength;
        this.previousPage = builder.previousPage;
        this.currentPage = builder.currentPage;
        this.nextPage = builder.nextPage;
        this.currentPageCount = builder.currentPageCount;
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.totalCount = builder.totalCount;
        this.totalPage = builder.totalPage;


    }

    public static GenericResponse.Builder ok() {
        return Builder.getInstance();
    }

    public static GenericResponse noContent() {
        return Builder.getInstance().noContent().build();
    }

    public static GenericResponse serverError() {
        return Builder.getInstance().serverError().build();
    }

    public static GenericResponse serverError(Object data) {
        return Builder.getInstance().serverError(data).build();
    }

    public static GenericResponse serverResourceError(Object data, ResponseStatus status, Integer statusCode) {
        return Builder.getInstance().serverResourceError(data, status, statusCode).build();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public Integer getCurrentPageCount() {
        return currentPageCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Object getData() {
        return data;
    }


    public ResponseStatus getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public static class Builder {

        private Object data;
        private ResponseStatus status;
        private Integer statusCode;
        private String contentType;
        private Integer contentLength;
        private Integer previousPage;
        private Integer currentPage;
        private Integer nextPage;
        private Integer currentPageCount;
        private Integer page;
        private Integer pageSize;
        private Integer totalCount;
        private Integer totalPage;
        private boolean search = false;

        static Builder getInstance() {
            return new Builder();
        }

        public Builder setData(Object data) {
            this.search = false;
            this.data = data;
            return this;
        }

        public Builder setSearchFilterData(Object data) {
            this.search = true;
            this.data = data;
            return this;
        }

        public Builder setStatus(ResponseStatus status) {
            this.status = status;
            return this;
        }

        public Builder setStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder setPage(Integer page) {
            if (page != null)
                this.page = page;
            return this;
        }

        public Builder setPageSize(Integer pageSize) {
            if (this.page != null)
                this.pageSize = pageSize;
            return this;
        }

        public Builder setTotalCount(Integer totalCount) {
            if (this.page != null)
                this.totalCount = totalCount;
            return this;
        }

        public Builder setContentLength(Integer contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        Builder noContent() {
            this.status = ResponseStatus.NO_CONTENT;
            this.statusCode = 204;
            return this;
        }

        Builder serverError() {
            this.data = "Oops! Something Went Wrong";
            this.status = ResponseStatus.INTERNAL_SERVER_ERROR;
            this.statusCode = 500;
            this.contentType = MediaType.TEXT_PLAIN_VALUE;
            this.contentLength = 1;
            return this;
        }

        Builder serverError(Object data) {
            this.data = data;
            this.status = ResponseStatus.INTERNAL_SERVER_ERROR;
            this.statusCode = 500;
            this.contentType = MediaType.TEXT_PLAIN_VALUE;
            this.contentLength = 1;
            return this;
        }

        Builder serverResourceError(Object data, ResponseStatus status, Integer statusCode) {
            this.data = data;
            this.status = status;
            this.statusCode = statusCode;
            this.contentType = MediaType.TEXT_PLAIN_VALUE;

            return this;
        }

        private void setOtherInfo() {
            this.contentType = MediaType.APPLICATION_JSON_VALUE;
            if (this.page != null) {

                if (this.pageSize != null && this.pageSize > 0) {
                    this.totalPage = (int) Math.ceil((double) this.totalCount / this.pageSize);
                }
                this.currentPageCount = ((List) this.data).size();
                this.previousPage = this.page == 0 ? 0 : this.page - 1;
                this.currentPage = this.page;
                this.nextPage = this.pageSize != null && this.currentPageCount >= this.pageSize ? this.page + 1 : 0;

            } else {
                this.contentLength = ((List) this.data).size();
            }

            if (this.status == null) {
                this.status = ((List) data).size() == 0 ? ResponseStatus.NO_CONTENT : ResponseStatus.SUCCESS;
            }
            if (this.statusCode == null) {
                this.statusCode = ((List) data).size() == 0 ? 204 : 200;
            }
            }

        private void setOtherInfo(Object data) {

            if (data != null) {
                if (data instanceof  HashMap) {
                    this.contentLength = ((HashMap) data).size();
                } else if (data instanceof List) {
                    this.contentLength = ((List) data).size();
                } else if (data instanceof  ArrayList) {
                    this.contentLength = ((ArrayList) data).size();
                } else if (data instanceof JSONObject) {
                    this.contentLength = ((JSONObject) data).size();
                } else if (data instanceof String) {
                    this.contentLength = ((String) data).length();
                } else if(data instanceof JSONArray){
                    this.contentLength = ((JSONArray)data).size();
                }

                if (this.status == null) {
                    this.status = ResponseStatus.SUCCESS;
                }
                if (this.statusCode == null) {
                    this.statusCode = 200;
                }

                if (this.contentType == null) {
                    this.contentType = MediaType.APPLICATION_JSON_VALUE;
                }
            }
        }

        public GenericResponse build() {
            if (search) {
                setOtherInfo();
            }
            else {
                setOtherInfo(data);
            }
            return new GenericResponse(this);
        }
    }
}
