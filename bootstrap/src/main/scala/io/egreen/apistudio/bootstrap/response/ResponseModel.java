package io.egreen.apistudio.bootstrap.response;

/**
 * Developed by (c) E-Green. (http://www.egreen.io).
 * *
 * <p>
 * Created by dewmal on 9/16/16.
 */

public class ResponseModel {

    private Type type;
    private Object message;
    private int code;


    public static final ResponseModelBuilder SUCCESS = new ResponseModelBuilder(new ResponseModel(Type.SUCCESS, "SUCCESS", 200));
    public static final ResponseModelBuilder EXCEPTION = new ResponseModelBuilder(new ResponseModel(Type.SUCCESS, "EXCEPTION", 500));
    public static final ResponseModelBuilder NOT_FOUND = new ResponseModelBuilder(new ResponseModel(Type.SUCCESS, "NOT_FOUND", 404));


    private ResponseModel() {

    }


    public ResponseModel(Type type, Object message, int code) {
        this.type = type;
        this.message = message;
        this.code = code;
    }

    public static final class ResponseModelBuilder {

        private final ResponseModel responseModel;

        public ResponseModelBuilder(ResponseModel responseModel) {
            this.responseModel = responseModel;
        }

        public ResponseModelBuilder() {
            this.responseModel = new ResponseModel();
        }

        public ResponseModelBuilder setType(Type type) {
            this.responseModel.setType(type);
            return this;
        }

        public ResponseModelBuilder setMessage(Object message) {
            this.responseModel.setMessage(message);
            return this;
        }

        public ResponseModelBuilder setCode(int code) {
            this.responseModel.setCode(code);
            return this;
        }

        public ResponseModel build() {
            return this.responseModel;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public enum Type {

        SUCCESS("success"),
        ERROR("error"),
        WARNING("warning"),
        INFO("info");
        private String type;

        Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Type{" +
                    "type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ReseponseMessage{" +
                "type=" + type +
                ", message=" + message +
                '}';
    }


}
