package starvationevasion.greninja.clientCommon;

/**
 * Enum of phase names.
 */
public enum EnumPhase
{
  DRAFTING
      {
        public String toString()
        {
          return "Policy Drafting Phase";
        }
      },
  VOTING
      {
        public String toString()
        {
          return "Policy Voting Phase";
        }
      }
}
