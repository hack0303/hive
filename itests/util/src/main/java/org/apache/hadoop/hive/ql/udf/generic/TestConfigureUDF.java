/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.ql.udf.generic;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.MapredContext;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;

@Description(name = "testconfigure", value = "_FUNC_(col) - UDF to test configure")
public class TestConfigureUDF extends GenericUDF {

  private MapredContext context;
  private Text result = new Text();

  public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
    return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
  }

  public Object evaluate(DeferredObject[] arguments) throws HiveException {
    result.set(context.getJobConf().get("testconf.udf.test"));
    return result;
  }

  public String getDisplayString(String[] children) {
    return "testconfigure()";
  }

  @Override
  public void configure(MapredContext context) {
    this.context = context;
    context.getJobConf().set("testconf.udf.test", "configure was called");
  }
}
