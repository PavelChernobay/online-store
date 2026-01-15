package org.onlinestore.common.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class InventoryServiceGrpc {

  private InventoryServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "inventory.InventoryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductGrpcRequest,
      org.onlinestore.common.grpc.ProductGrpcResponse> getGetProductByNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getProductByName",
      requestType = org.onlinestore.common.grpc.ProductGrpcRequest.class,
      responseType = org.onlinestore.common.grpc.ProductGrpcResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductGrpcRequest,
      org.onlinestore.common.grpc.ProductGrpcResponse> getGetProductByNameMethod() {
    io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductGrpcRequest, org.onlinestore.common.grpc.ProductGrpcResponse> getGetProductByNameMethod;
    if ((getGetProductByNameMethod = InventoryServiceGrpc.getGetProductByNameMethod) == null) {
      synchronized (InventoryServiceGrpc.class) {
        if ((getGetProductByNameMethod = InventoryServiceGrpc.getGetProductByNameMethod) == null) {
          InventoryServiceGrpc.getGetProductByNameMethod = getGetProductByNameMethod =
              io.grpc.MethodDescriptor.<org.onlinestore.common.grpc.ProductGrpcRequest, org.onlinestore.common.grpc.ProductGrpcResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getProductByName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.onlinestore.common.grpc.ProductGrpcRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.onlinestore.common.grpc.ProductGrpcResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InventoryServiceMethodDescriptorSupplier("getProductByName"))
              .build();
        }
      }
    }
    return getGetProductByNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductBatchGrpcRequest,
      org.onlinestore.common.grpc.ProductBatchGrpcResponse> getGetListProductByNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getListProductByName",
      requestType = org.onlinestore.common.grpc.ProductBatchGrpcRequest.class,
      responseType = org.onlinestore.common.grpc.ProductBatchGrpcResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductBatchGrpcRequest,
      org.onlinestore.common.grpc.ProductBatchGrpcResponse> getGetListProductByNameMethod() {
    io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductBatchGrpcRequest, org.onlinestore.common.grpc.ProductBatchGrpcResponse> getGetListProductByNameMethod;
    if ((getGetListProductByNameMethod = InventoryServiceGrpc.getGetListProductByNameMethod) == null) {
      synchronized (InventoryServiceGrpc.class) {
        if ((getGetListProductByNameMethod = InventoryServiceGrpc.getGetListProductByNameMethod) == null) {
          InventoryServiceGrpc.getGetListProductByNameMethod = getGetListProductByNameMethod =
              io.grpc.MethodDescriptor.<org.onlinestore.common.grpc.ProductBatchGrpcRequest, org.onlinestore.common.grpc.ProductBatchGrpcResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getListProductByName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.onlinestore.common.grpc.ProductBatchGrpcRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.onlinestore.common.grpc.ProductBatchGrpcResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InventoryServiceMethodDescriptorSupplier("getListProductByName"))
              .build();
        }
      }
    }
    return getGetListProductByNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductQuantityBatch,
      com.google.protobuf.Empty> getUpdateProductQuantitiesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateProductQuantities",
      requestType = org.onlinestore.common.grpc.ProductQuantityBatch.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductQuantityBatch,
      com.google.protobuf.Empty> getUpdateProductQuantitiesMethod() {
    io.grpc.MethodDescriptor<org.onlinestore.common.grpc.ProductQuantityBatch, com.google.protobuf.Empty> getUpdateProductQuantitiesMethod;
    if ((getUpdateProductQuantitiesMethod = InventoryServiceGrpc.getUpdateProductQuantitiesMethod) == null) {
      synchronized (InventoryServiceGrpc.class) {
        if ((getUpdateProductQuantitiesMethod = InventoryServiceGrpc.getUpdateProductQuantitiesMethod) == null) {
          InventoryServiceGrpc.getUpdateProductQuantitiesMethod = getUpdateProductQuantitiesMethod =
              io.grpc.MethodDescriptor.<org.onlinestore.common.grpc.ProductQuantityBatch, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateProductQuantities"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.onlinestore.common.grpc.ProductQuantityBatch.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new InventoryServiceMethodDescriptorSupplier("updateProductQuantities"))
              .build();
        }
      }
    }
    return getUpdateProductQuantitiesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InventoryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InventoryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InventoryServiceStub>() {
        @java.lang.Override
        public InventoryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InventoryServiceStub(channel, callOptions);
        }
      };
    return InventoryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static InventoryServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InventoryServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InventoryServiceBlockingV2Stub>() {
        @java.lang.Override
        public InventoryServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InventoryServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return InventoryServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InventoryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InventoryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InventoryServiceBlockingStub>() {
        @java.lang.Override
        public InventoryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InventoryServiceBlockingStub(channel, callOptions);
        }
      };
    return InventoryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InventoryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InventoryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InventoryServiceFutureStub>() {
        @java.lang.Override
        public InventoryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InventoryServiceFutureStub(channel, callOptions);
        }
      };
    return InventoryServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getProductByName(org.onlinestore.common.grpc.ProductGrpcRequest request,
        io.grpc.stub.StreamObserver<org.onlinestore.common.grpc.ProductGrpcResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProductByNameMethod(), responseObserver);
    }

    /**
     */
    default void getListProductByName(org.onlinestore.common.grpc.ProductBatchGrpcRequest request,
        io.grpc.stub.StreamObserver<org.onlinestore.common.grpc.ProductBatchGrpcResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetListProductByNameMethod(), responseObserver);
    }

    /**
     */
    default void updateProductQuantities(org.onlinestore.common.grpc.ProductQuantityBatch request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateProductQuantitiesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service InventoryService.
   */
  public static abstract class InventoryServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return InventoryServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service InventoryService.
   */
  public static final class InventoryServiceStub
      extends io.grpc.stub.AbstractAsyncStub<InventoryServiceStub> {
    private InventoryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InventoryServiceStub(channel, callOptions);
    }

    /**
     */
    public void getProductByName(org.onlinestore.common.grpc.ProductGrpcRequest request,
        io.grpc.stub.StreamObserver<org.onlinestore.common.grpc.ProductGrpcResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProductByNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getListProductByName(org.onlinestore.common.grpc.ProductBatchGrpcRequest request,
        io.grpc.stub.StreamObserver<org.onlinestore.common.grpc.ProductBatchGrpcResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetListProductByNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateProductQuantities(org.onlinestore.common.grpc.ProductQuantityBatch request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateProductQuantitiesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service InventoryService.
   */
  public static final class InventoryServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<InventoryServiceBlockingV2Stub> {
    private InventoryServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InventoryServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public org.onlinestore.common.grpc.ProductGrpcResponse getProductByName(org.onlinestore.common.grpc.ProductGrpcRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetProductByNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.onlinestore.common.grpc.ProductBatchGrpcResponse getListProductByName(org.onlinestore.common.grpc.ProductBatchGrpcRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetListProductByNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateProductQuantities(org.onlinestore.common.grpc.ProductQuantityBatch request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getUpdateProductQuantitiesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service InventoryService.
   */
  public static final class InventoryServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<InventoryServiceBlockingStub> {
    private InventoryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InventoryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.onlinestore.common.grpc.ProductGrpcResponse getProductByName(org.onlinestore.common.grpc.ProductGrpcRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProductByNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.onlinestore.common.grpc.ProductBatchGrpcResponse getListProductByName(org.onlinestore.common.grpc.ProductBatchGrpcRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetListProductByNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty updateProductQuantities(org.onlinestore.common.grpc.ProductQuantityBatch request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateProductQuantitiesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service InventoryService.
   */
  public static final class InventoryServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<InventoryServiceFutureStub> {
    private InventoryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InventoryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InventoryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.onlinestore.common.grpc.ProductGrpcResponse> getProductByName(
        org.onlinestore.common.grpc.ProductGrpcRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProductByNameMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.onlinestore.common.grpc.ProductBatchGrpcResponse> getListProductByName(
        org.onlinestore.common.grpc.ProductBatchGrpcRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetListProductByNameMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> updateProductQuantities(
        org.onlinestore.common.grpc.ProductQuantityBatch request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateProductQuantitiesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PRODUCT_BY_NAME = 0;
  private static final int METHODID_GET_LIST_PRODUCT_BY_NAME = 1;
  private static final int METHODID_UPDATE_PRODUCT_QUANTITIES = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PRODUCT_BY_NAME:
          serviceImpl.getProductByName((org.onlinestore.common.grpc.ProductGrpcRequest) request,
              (io.grpc.stub.StreamObserver<org.onlinestore.common.grpc.ProductGrpcResponse>) responseObserver);
          break;
        case METHODID_GET_LIST_PRODUCT_BY_NAME:
          serviceImpl.getListProductByName((org.onlinestore.common.grpc.ProductBatchGrpcRequest) request,
              (io.grpc.stub.StreamObserver<org.onlinestore.common.grpc.ProductBatchGrpcResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PRODUCT_QUANTITIES:
          serviceImpl.updateProductQuantities((org.onlinestore.common.grpc.ProductQuantityBatch) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetProductByNameMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.onlinestore.common.grpc.ProductGrpcRequest,
              org.onlinestore.common.grpc.ProductGrpcResponse>(
                service, METHODID_GET_PRODUCT_BY_NAME)))
        .addMethod(
          getGetListProductByNameMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.onlinestore.common.grpc.ProductBatchGrpcRequest,
              org.onlinestore.common.grpc.ProductBatchGrpcResponse>(
                service, METHODID_GET_LIST_PRODUCT_BY_NAME)))
        .addMethod(
          getUpdateProductQuantitiesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.onlinestore.common.grpc.ProductQuantityBatch,
              com.google.protobuf.Empty>(
                service, METHODID_UPDATE_PRODUCT_QUANTITIES)))
        .build();
  }

  private static abstract class InventoryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InventoryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.onlinestore.common.grpc.ProductProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InventoryService");
    }
  }

  private static final class InventoryServiceFileDescriptorSupplier
      extends InventoryServiceBaseDescriptorSupplier {
    InventoryServiceFileDescriptorSupplier() {}
  }

  private static final class InventoryServiceMethodDescriptorSupplier
      extends InventoryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    InventoryServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InventoryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InventoryServiceFileDescriptorSupplier())
              .addMethod(getGetProductByNameMethod())
              .addMethod(getGetListProductByNameMethod())
              .addMethod(getUpdateProductQuantitiesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
