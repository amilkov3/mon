package io.voklim.mon.metrics

import io.voklim.mon.test._

class MetricKeysTest extends UnitPropertySpec {

  property("should create metric key string correctly") {
    val expected = s"${metricTestKey.keyPrefix.toString}.${metricTestKey.keyDomain.toString}.${metricTestKey.metricName.toString}"
    val actual = metricTestKey.toKeyString
    actual should be(expected)
  }

  property("should create metric key string correctly with suffix parts") {
    forAll { suffixParts: (String, String) =>
      val expected = s"${metricTestKey.keyPrefix.toString}.${metricTestKey.keyDomain.toString}.${metricTestKey.metricName.toString}.${suffixParts._1}.${suffixParts._2}"
      val actual = MetricKey.createWithSuffix(
        metricTestKey.keyPrefix,
        metricTestKey.keyDomain,
        metricTestKey.metricName,
        suffixParts._1,
        suffixParts._2
      ).toKeyString
      actual should be(expected)
    }
  }

}
