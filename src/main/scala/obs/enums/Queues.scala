package obs.enums

import obs.enums

object Queues extends Enumeration {
  type Queues=Value
  val GET_QUEUE_NAME: enums.Queues.Value =Value("get_queue")
  val CREATE_QUEUE_NAME: enums.Queues.Value =Value("create_queue")
  val SEARCH_QUEUE_NAME: enums.Queues.Value =Value("search_queue")
}
