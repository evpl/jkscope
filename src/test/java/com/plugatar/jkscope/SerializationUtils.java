package com.plugatar.jkscope;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

final class SerializationUtils {

  private SerializationUtils() {
  }

  static byte[] serialize(final Object obj) throws IOException {
    try (ByteArrayOutputStream boas = new ByteArrayOutputStream();
         ObjectOutputStream ois = new ObjectOutputStream(boas)) {
      ois.writeObject(obj);
      return boas.toByteArray();
    }
  }

  static Object deserialize(final byte[] bytes) throws IOException, ClassNotFoundException {
    try (InputStream is = new ByteArrayInputStream(bytes);
         ObjectInputStream ois = new ObjectInputStream(is)) {
      return ois.readObject();
    }
  }
}
