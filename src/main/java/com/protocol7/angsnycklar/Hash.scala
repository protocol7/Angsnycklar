package com.protocol7.angsnycklar

import java.math.BigInteger
import org.apache.commons.codec.digest.DigestUtils


object Hash {

  def hash(s: String): BigInt = {
    new BigInt(new BigInteger(DigestUtils.sha(s)))
  }

}