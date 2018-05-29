package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle.Event;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    private static final int CALL_TYPE_NO_ARG = 0;
    private static final int CALL_TYPE_PROVIDER = 1;
    private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
    static final Map<Class, CallbackInfo> sInfoCache;
    private final CallbackInfo mInfo;
    private final Object mWrapped;

    static class CallbackInfo {
        final Map<Event, List<MethodReference>> mEventToHandlers;
        final Map<MethodReference, Event> mHandlerToEvent;

        CallbackInfo(Map<MethodReference, Event> handlerToEvent) {
            this.mHandlerToEvent = handlerToEvent;
            this.mEventToHandlers = new HashMap();
            for (Entry<MethodReference, Event> entry : handlerToEvent.entrySet()) {
                Event event = (Event) entry.getValue();
                List<MethodReference> methodReferences = (List) this.mEventToHandlers.get(event);
                if (methodReferences == null) {
                    methodReferences = new ArrayList();
                    this.mEventToHandlers.put(event, methodReferences);
                }
                methodReferences.add(entry.getKey());
            }
        }
    }

    static class MethodReference {
        final int mCallType;
        final Method mMethod;

        MethodReference(int callType, Method method) {
            this.mCallType = callType;
            this.mMethod = method;
            this.mMethod.setAccessible(true);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MethodReference that = (MethodReference) o;
            return this.mCallType == that.mCallType && this.mMethod.getName().equals(that.mMethod.getName());
        }

        public int hashCode() {
            return (this.mCallType * 31) + this.mMethod.getName().hashCode();
        }
    }

    static {
        sInfoCache = new HashMap();
    }

    ReflectiveGenericLifecycleObserver(Object wrapped) {
        this.mWrapped = wrapped;
        this.mInfo = getInfo(this.mWrapped.getClass());
    }

    public void onStateChanged(LifecycleOwner source, Event event) {
        invokeCallbacks(this.mInfo, source, event);
    }

    private void invokeMethodsForEvent(List<MethodReference> handlers, LifecycleOwner source, Event event) {
        if (handlers != null) {
            for (int i = handlers.size() - 1; i >= 0; i--) {
                invokeCallback((MethodReference) handlers.get(i), source, event);
            }
        }
    }

    private void invokeCallbacks(CallbackInfo info, LifecycleOwner source, Event event) {
        invokeMethodsForEvent((List) info.mEventToHandlers.get(event), source, event);
        invokeMethodsForEvent((List) info.mEventToHandlers.get(Event.ON_ANY), source, event);
    }

    private void invokeCallback(MethodReference reference, LifecycleOwner source, Event event) {
        try {
            switch (reference.mCallType) {
                case CALL_TYPE_NO_ARG:
                    reference.mMethod.invoke(this.mWrapped, new Object[0]);
                case CALL_TYPE_PROVIDER:
                    reference.mMethod.invoke(this.mWrapped, new Object[]{source});
                case CALL_TYPE_PROVIDER_WITH_EVENT:
                    reference.mMethod.invoke(this.mWrapped, new Object[]{source, event});
                default:
                    break;
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to call observer method", e.getCause());
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static CallbackInfo getInfo(Class klass) {
        CallbackInfo existing = (CallbackInfo) sInfoCache.get(klass);
        return existing != null ? existing : createInfo(klass);
    }

    private static void verifyAndPutHandler(Map<MethodReference, Event> handlers, MethodReference newHandler, Event newEvent, Class klass) {
        Event event = (Event) handlers.get(newHandler);
        if (event != null && newEvent != event) {
            throw new IllegalArgumentException("Method " + newHandler.mMethod.getName() + " in " + klass.getName() + " already declared with different @OnLifecycleEvent value: previous" + " value " + event + ", new value " + newEvent);
        } else if (event == null) {
            handlers.put(newHandler, newEvent);
        }
    }

    private static CallbackInfo createInfo(Class klass) {
        Class superclass = klass.getSuperclass();
        Map<MethodReference, Event> handlerToEvent = new HashMap();
        if (superclass != null) {
            CallbackInfo superInfo = getInfo(superclass);
            if (superInfo != null) {
                handlerToEvent.putAll(superInfo.mHandlerToEvent);
            }
        }
        Method[] methods = klass.getDeclaredMethods();
        Class[] interfaces = klass.getInterfaces();
        int length = interfaces.length;
        for (int i = 0; i < length; i++) {
            for (Entry<MethodReference, Event> entry : getInfo(interfaces[i]).mHandlerToEvent.entrySet()) {
                verifyAndPutHandler(handlerToEvent, (MethodReference) entry.getKey(), (Event) entry.getValue(), klass);
            }
        }
        int length2 = methods.length;
        for (int i2 = CALL_TYPE_NO_ARG; i2 < length2; i2++) {
            Method method = methods[i2];
            OnLifecycleEvent annotation = (OnLifecycleEvent) method.getAnnotation(OnLifecycleEvent.class);
            if (annotation != null) {
                Class<?>[] params = method.getParameterTypes();
                int i3 = CALL_TYPE_NO_ARG;
                if (params.length > 0) {
                    i3 = CALL_TYPE_PROVIDER;
                    if (!params[0].isAssignableFrom(LifecycleOwner.class)) {
                        throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                    }
                }
                Event event = annotation.value();
                if (params.length > 1) {
                    i3 = CALL_TYPE_PROVIDER_WITH_EVENT;
                    if (!params[1].isAssignableFrom(Event.class)) {
                        throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                    } else if (event != Event.ON_ANY) {
                        throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
                    }
                }
                if (params.length > 2) {
                    throw new IllegalArgumentException("cannot have more than 2 params");
                }
                verifyAndPutHandler(handlerToEvent, new MethodReference(i3, method), event, klass);
            }
        }
        CallbackInfo info = new CallbackInfo(handlerToEvent);
        sInfoCache.put(klass, info);
        return info;
    }
}
