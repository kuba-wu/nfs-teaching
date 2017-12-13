// SINGLE
{
  "components": [
    {
      "id": "first",
      "receptor": {
        "delay": 5,
        "thresholds": {
          "": 15
        }
      },
      "effector": {
        "production": 2,
        "outflow": 1,
      }
    }
  ]
}
// TWO CONNECTED
{
  "components": [
    {
      "id": "second",
	  "receptor": {
		"delay": 0,
		"thresholds" : {
			"" : 7
		}
	  },
	  "effector": {
		"production": 3,
		"outflow": 1
	  }
    },
    {
      "id": "first",
      "receptor": {
        "delay": 3,
        "thresholds": {
          "": 10
        }
      },
      "effector": {
        "production": 5,
        "outflow": 1,
		"source": "second"
      }
    }
  ]
}
// TWO - COORDINATION
{
  "receptors": [
	{
		"id" : "coordinator",
		"delay" : 5,
		"thresholds" : {
			"" : 12
		}
	}
  ],
  "components": [
    {
      "id": "first",
      "receptor": {
        "delay": 3,
        "thresholds": {
          "": 10,
		  "coordinator": 15
        }
      },
      "effector": {
        "production": 2,
        "outflow": 1
      }
    }
  ]
}
// THREE CONNECTED IN A CYCLE
{
  "components": [
    {
      "id": "first",
      "receptor": {
        "delay": 3,
        "thresholds": {
          "": 17
        }
      },
      "effector": {
        "production": 5,
        "outflow": 1,
		"source": "third"
      }
    },  
    {
      "id": "second",
	  "receptor": {
		"delay": 2,
		"thresholds" : {
			"" : 16
		}
	  },
	  "effector": {
		"production": 3,
		"outflow": 1,
		"source": "first"
	  }
    },
	{
      "id": "third",
	  "receptor": {
		"delay": 2,
		"thresholds" : {
			"" : 20
		}
	  },
	  "effector": {
		"production": 3,
		"outflow": 1,
		"source": "second"
	  }
    }
  ],
  "init" : {
	"third" : 10
  }
}